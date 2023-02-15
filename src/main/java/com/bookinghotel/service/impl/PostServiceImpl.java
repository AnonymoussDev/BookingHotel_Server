package com.bookinghotel.service.impl;

import com.bookinghotel.constant.CommonConstant;
import com.bookinghotel.constant.CommonMessage;
import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.constant.SortByDataConstant;
import com.bookinghotel.dto.PostCreateDTO;
import com.bookinghotel.dto.PostDTO;
import com.bookinghotel.dto.PostUpdateDTO;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.dto.pagination.PaginationResponseDTO;
import com.bookinghotel.dto.pagination.PaginationSortRequestDTO;
import com.bookinghotel.dto.pagination.PagingMeta;
import com.bookinghotel.entity.Media;
import com.bookinghotel.entity.Post;
import com.bookinghotel.entity.User;
import com.bookinghotel.exception.InternalServerException;
import com.bookinghotel.exception.NotFoundException;
import com.bookinghotel.mapper.MediaMapper;
import com.bookinghotel.mapper.PostMapper;
import com.bookinghotel.repository.MediaRepository;
import com.bookinghotel.repository.PostRepository;
import com.bookinghotel.repository.UserRepository;
import com.bookinghotel.security.UserPrincipal;
import com.bookinghotel.service.PostService;
import com.bookinghotel.util.PaginationUtil;
import com.bookinghotel.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;

  private final UserRepository userRepository;

  private final MediaRepository mediaRepository;

  private final PostMapper postMapper;

  private final MediaMapper mediaMapper;

  private final UploadFileUtil uploadFile;

  @Override
  public PostDTO getPost(Long postId) {
    Optional<Post> post = postRepository.findById(postId);
    checkNotFoundPostById(post, postId);
    post.get().setMedias(mediaRepository.findByPostToSet(postId));
    return postMapper.toPostDTO(post.get(), post.get().getUser());
  }

  @Override
  public PaginationResponseDTO<PostDTO> getPosts(PaginationSortRequestDTO requestDTO) {
    //Pagination
    Pageable pageable = PaginationUtil.buildPageable(requestDTO, SortByDataConstant.POST);
    Page<Post> posts = postRepository.findAll(pageable);

    //Create Output
    PagingMeta meta = PaginationUtil.buildPagingMeta(requestDTO, SortByDataConstant.POST, posts);
    List<PostDTO> roomDTOs = postMapper.toPostDTOs(posts.getContent());
    return new PaginationResponseDTO<PostDTO>(meta, roomDTOs);
  }

  @Override
  public PostDTO createPost(PostCreateDTO createDTO, UserPrincipal currentUser) {
    User user = userRepository.getUser(currentUser);

    Post post = postMapper.createDtoToPost(createDTO);
    post.setUser(user);
    postRepository.save(post);
    if (createDTO.getFiles() != null) {
      post.setMedias(toMedias(post, createDTO.getFiles()));
    }
    return postMapper.toPostDTO(postRepository.save(post), user);
  }

  @Override
  public PostDTO updatePost(Long postId, PostUpdateDTO updateDTO) {
    Optional<Post> currentPost = postRepository.findById(postId);
    checkNotFoundPostById(currentPost, postId);

    //Delete media if not found in mediaDTO
    if (CollectionUtils.isNotEmpty(updateDTO.getMedias())) {
      List<Media> medias = mediaMapper.toMedias(updateDTO.getMedias());
      List<Media> mediaDeleteFlag = mediaRepository.findByPostIdAndNotInMedia(postId, medias);
      if (CollectionUtils.isNotEmpty(mediaDeleteFlag)) {
        mediaDeleteFlag.forEach(item -> {
          item.setDeleteFlag(CommonConstant.TRUE);
          mediaRepository.save(item);
        });
      }
    } else {
      List<Media> mediaDeleteFlag = mediaRepository.findByPostId(postId);
      if (CollectionUtils.isNotEmpty(mediaDeleteFlag)) {
        mediaDeleteFlag.forEach(item -> {
          item.setDeleteFlag(CommonConstant.TRUE);
          mediaRepository.save(item);
        });
      }
    }
    //add file if exist
    if (updateDTO.getFiles() != null) {
      currentPost.get().getMedias().addAll(toMedias(currentPost.get(), updateDTO.getFiles()));
    }

    postMapper.updatePostFromDTO(updateDTO, currentPost.get());
    currentPost.get().setMedias(mediaRepository.findByPostToSet(postId));
    return postMapper.toPostDTO(postRepository.save(currentPost.get()), currentPost.get().getUser());
  }

  @Override
  @Transactional
  public CommonResponseDTO deletePost(Long postId) {
    Optional<Post> post = postRepository.findById(postId);
    checkNotFoundPostById(post, postId);

    try {
      post.get().setDeleteFlag(CommonConstant.TRUE);
      //set deleteFlag Media
      List<Media> mediaDeleteFlag = mediaRepository.findByPostId(postId);
      if (CollectionUtils.isNotEmpty(mediaDeleteFlag)) {
        mediaDeleteFlag.forEach(item -> {
          item.setDeleteFlag(CommonConstant.TRUE);
          mediaRepository.save(item);
        });
      }
      postRepository.save(post.get());
      return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.DELETE_SUCCESS);
    } catch (Exception e) {
      throw new InternalServerException(ErrorMessage.ERR_EXCEPTION_GENERAL);
    }
  }

  private Set<Media> toMedias(Post post, List<MultipartFile> files) {
    Set<Media> medias = new HashSet<>();
    for (MultipartFile file : files) {
      Media media = new Media();
      media.setUrl(uploadFile.getUrlFromFile(file));
      media.setPost(post);
      mediaRepository.save(media);
      medias.add(media);
    }
    return medias;
  }

  private void checkNotFoundPostById(Optional<Post> post, Long postId) {
    if (post.isEmpty()) {
      throw new NotFoundException(String.format(ErrorMessage.Post.ERR_NOT_FOUND_ID, postId));
    }
  }

}
