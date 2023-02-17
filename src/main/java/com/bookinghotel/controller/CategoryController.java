package com.bookinghotel.controller;

import com.bookinghotel.base.RestApiV1;
import com.bookinghotel.base.VsResponseUtil;
import com.bookinghotel.constant.UrlConstant;
import com.bookinghotel.dto.pagination.PaginationSearchSortRequestDTO;
import com.bookinghotel.service.CategoryService;
import com.bookinghotel.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class CategoryController {

  private final CategoryService categoryService;

  private final ProductService productService;

  @Operation(summary = "API get category by id")
  @GetMapping(UrlConstant.Category.GET_CATEGORIES)
  public ResponseEntity<?> getCategories() {
    return VsResponseUtil.ok(categoryService.getCategories());
  }

  @Operation(summary = "API get all product by category")
  @GetMapping(UrlConstant.Category.GET_PRODUCTS_CATEGORY)
  public ResponseEntity<?> getProductsByCategory(@PathVariable Long categoryId,
                                                 @Valid PaginationSearchSortRequestDTO requestDTO) {
    return VsResponseUtil.ok(productService.getProductsByCategoryId(categoryId, requestDTO));
  }

  @Operation(summary = "API create category")
  @PostMapping(UrlConstant.Category.CREATE_CATEGORY)
  public ResponseEntity<?> createCategoryById(@RequestParam String name) {
    return VsResponseUtil.ok(categoryService.createCategory(name));
  }

  @Operation(summary = "API update category by id")
  @PutMapping(UrlConstant.Category.UPDATE_CATEGORY)
  public ResponseEntity<?> updateCategoryById(@PathVariable Long categoryId, @RequestParam String name) {
    return VsResponseUtil.ok(categoryService.updateCategory(categoryId, name));
  }

  @Operation(summary = "API delete category by id")
  @DeleteMapping(UrlConstant.Category.DELETE_CATEGORY)
  public ResponseEntity<?> deleteCategoryById(@PathVariable Long categoryId) {
    return VsResponseUtil.ok(categoryService.deleteCategory(categoryId));
  }

}
