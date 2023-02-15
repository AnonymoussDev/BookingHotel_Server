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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class PostController {

  private final PostService postService;

  @ApiOperation("API get post by id")
  @GetMapping(UrlConstant.Post.GET_POST)
  public ResponseEntity<?> getPostById(@PathVariable Long postId) {
    return VsResponseUtil.ok(postService.getPost(postId));
  }

  @ApiOperation("API get all post")
  @GetMapping(UrlConstant.Post.GET_POSTS)
  public ResponseEntity<?> getPosts(@Valid PaginationSortRequestDTO requestDTO) {
    return VsResponseUtil.ok(postService.getPosts(requestDTO));
  }

  @ApiOperation("API create post")
  @PostMapping(UrlConstant.Post.CREATE_POST)
  public ResponseEntity<?> createPostById(@Valid PostCreateDTO createDTO, @CurrentUserLogin UserPrincipal principal) {
    return VsResponseUtil.ok(postService.createPost(createDTO, principal));
  }

  @ApiOperation(value = "API update post by id")
  @PutMapping(UrlConstant.Post.UPDATE_POST)
  public ResponseEntity<?> updateRoomById(@PathVariable Long postId, @Valid @RequestBody PostUpdateDTO updateDTO) {
    return VsResponseUtil.ok(postService.updatePost(postId, updateDTO));
  }

  @ApiOperation("API delete post by id")
  @DeleteMapping(UrlConstant.Post.DELETE_POST)
  public ResponseEntity<?> deletePostById(@PathVariable Long postId) {
    return VsResponseUtil.ok(postService.deletePost(postId));
  }

}
