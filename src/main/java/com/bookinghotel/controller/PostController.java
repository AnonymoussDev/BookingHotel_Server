package com.bookinghotel.controller;

import com.bookinghotel.base.RestApiV1;
import com.bookinghotel.base.VsResponseUtil;
import com.bookinghotel.constant.UrlConstant;
import com.bookinghotel.dto.PostCreateDTO;
import com.bookinghotel.dto.PostUpdateDTO;
import com.bookinghotel.dto.pagination.PaginationSortRequestDTO;
import com.bookinghotel.security.CurrentUserLogin;
import com.bookinghotel.security.UserPrincipal;
import com.bookinghotel.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class PostController {

  private final PostService postService;

  @Operation(summary = "API get post by id")
  @GetMapping(UrlConstant.Post.GET_POST)
  public ResponseEntity<?> getPostById(@PathVariable Long postId) {
    return VsResponseUtil.ok(postService.getPost(postId));
  }

  @Operation(summary = "API get all post")
  @GetMapping(UrlConstant.Post.GET_POSTS)
  public ResponseEntity<?> getPosts(@Valid PaginationSortRequestDTO requestDTO) {
    return VsResponseUtil.ok(postService.getPosts(requestDTO));
  }

  @Operation(summary = "API create post")
  @Parameter(name = "principal", hidden = true)
  @PostMapping(value = UrlConstant.Post.CREATE_POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> createPostById(@Valid @ModelAttribute PostCreateDTO postCreateDTO,
                                          @CurrentUserLogin UserPrincipal principal) {
    return VsResponseUtil.ok(postService.createPost(postCreateDTO, principal));
  }

  @Operation(summary = "API update post by id")
  @PutMapping(value = UrlConstant.Post.UPDATE_POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> updatePostById(@PathVariable Long postId, @Valid @ModelAttribute PostUpdateDTO postUpdateDTO) {
    return VsResponseUtil.ok(postService.updatePost(postId, postUpdateDTO));
  }

  @Operation(summary = "API delete post by id")
  @DeleteMapping(UrlConstant.Post.DELETE_POST)
  public ResponseEntity<?> deletePostById(@PathVariable Long postId) {
    return VsResponseUtil.ok(postService.deletePost(postId));
  }

}
