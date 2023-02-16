package com.bookinghotel.controller;

import com.bookinghotel.base.RestApiV1;
import com.bookinghotel.base.VsResponseUtil;
import com.bookinghotel.constant.UrlConstant;
import com.bookinghotel.dto.ProductCreateDTO;
import com.bookinghotel.dto.ProductUpdateDTO;
import com.bookinghotel.dto.pagination.PaginationSearchSortRequestDTO;
import com.bookinghotel.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class ProductController {

  private final ProductService productService;

  @ApiOperation("API get product by id")
  @GetMapping(UrlConstant.Product.GET_PRODUCT)
  public ResponseEntity<?> getProductById(@PathVariable Long productId) {
    return VsResponseUtil.ok(productService.getProduct(productId));
  }

  @ApiOperation("API get all product")
  @GetMapping(UrlConstant.Product.GET_PRODUCTS)
  public ResponseEntity<?> getProducts(@Valid PaginationSearchSortRequestDTO requestDTO) {
    return VsResponseUtil.ok(productService.getProducts(requestDTO));
  }

  @ApiOperation("API create product")
  @PostMapping(UrlConstant.Product.CREATE_PRODUCT)
  public ResponseEntity<?> createProductById(@Valid ProductCreateDTO productCreateDTO) {
    return VsResponseUtil.ok(productService.createProduct(productCreateDTO));
  }

  @ApiOperation(value = "API update product by id")
  @PutMapping(UrlConstant.Product.UPDATE_PRODUCT)
  public ResponseEntity<?> updateProductById(@PathVariable Long productId, @Valid ProductUpdateDTO productUpdateDTO) {
    return VsResponseUtil.ok(productService.updateProduct(productId, productUpdateDTO));
  }

  @ApiOperation("API delete product by id")
  @DeleteMapping(UrlConstant.Product.DELETE_PRODUCTS)
  public ResponseEntity<?> deleteProductById(@PathVariable Long productId) {
    return VsResponseUtil.ok(productService.deleteProduct(productId));
  }

}
