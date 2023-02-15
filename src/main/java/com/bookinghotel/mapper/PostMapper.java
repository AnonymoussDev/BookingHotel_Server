package com.bookinghotel.mapper;

import com.bookinghotel.dto.MediaDTO;
import com.bookinghotel.dto.PostCreateDTO;
import com.bookinghotel.dto.PostDTO;
import com.bookinghotel.dto.PostUpdateDTO;
import com.bookinghotel.entity.Media;
import com.bookinghotel.entity.Post;
import com.bookinghotel.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

  @Mappings({
      @Mapping(target = "id", source = "post.id"),
      @Mapping(target = "createdDate", source = "post.createdDate"),
      @Mapping(target = "lastModifiedDate", source = "post.lastModifiedDate"),
      @Mapping(target = "createdBy.id", source = "user.id"),
      @Mapping(target = "createdBy.firstName", source = "user.firstName"),
      @Mapping(target = "createdBy.lastName", source = "user.lastName")
  })
  PostDTO toPostDTO(Post post, User user);

  @Mappings({
      @Mapping(target = "id", source = "post.id"),
      @Mapping(target = "createdDate", source = "post.createdDate"),
      @Mapping(target = "lastModifiedDate", source = "post.lastModifiedDate"),
      @Mapping(target = "createdBy.id", source = "post.user.id"),
      @Mapping(target = "createdBy.firstName", source = "post.user.firstName"),
      @Mapping(target = "createdBy.lastName", source = "post.user.lastName")
  })
  PostDTO toPostDTO(Post post);

  MediaDTO toMediaDTO(Media media);

  List<PostDTO> toPostDTOs(List<Post> posts);

  Post createDtoToPost(PostCreateDTO createDTO);

  @Mapping(target = "medias", ignore = true)
  void updatePostFromDTO(PostUpdateDTO updateDTO, @MappingTarget Post post);

}
