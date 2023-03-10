package com.bookinghotel.service;

import com.bookinghotel.dto.PostCreateDTO;
import com.bookinghotel.dto.PostDTO;
import com.bookinghotel.dto.PostUpdateDTO;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.dto.pagination.PaginationResponseDTO;
import com.bookinghotel.dto.pagination.PaginationSortRequestDTO;
import com.bookinghotel.security.UserPrincipal;

public interface PostService {

  PostDTO getPost(Long postId);

  PaginationResponseDTO<PostDTO> getPosts(PaginationSortRequestDTO requestDTO);

  PostDTO createPost(PostCreateDTO createDTO, UserPrincipal currentUser);

  PostDTO updatePost(Long postId, PostUpdateDTO updateDTO);

  CommonResponseDTO deletePost(Long postId);

}
