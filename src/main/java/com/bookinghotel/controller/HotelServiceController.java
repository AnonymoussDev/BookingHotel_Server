package com.bookinghotel.controller;

import com.bookinghotel.base.RestApiV1;
import com.bookinghotel.base.VsResponseUtil;
import com.bookinghotel.constant.UrlConstant;
import com.bookinghotel.dto.ServiceCreateDTO;
import com.bookinghotel.dto.ServiceUpdateDTO;
import com.bookinghotel.dto.pagination.PaginationSearchSortRequestDTO;
import com.bookinghotel.service.HotelService;
import com.bookinghotel.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class HotelServiceController {

  private final HotelService hotelService;

  private final ProductService productService;

  @Operation(summary = "API get service by id")
  @GetMapping(UrlConstant.Service.GET_SERVICE)
  public ResponseEntity<?> getServiceById(@PathVariable Long serviceId) {
    return VsResponseUtil.ok(hotelService.getServiceById(serviceId));
  }

  @Operation(summary = "API get all service")
  @GetMapping(UrlConstant.Service.GET_SERVICES)
  public ResponseEntity<?> getServices(@Valid PaginationSearchSortRequestDTO requestDTO) {
    return VsResponseUtil.ok(hotelService.getServices(requestDTO));
  }

  @Operation(summary = "API get all product by service")
  @GetMapping(UrlConstant.Service.GET_PRODUCTS_BY_SERVICE)
  public ResponseEntity<?> getProductsByService(@PathVariable Long serviceId, @Valid PaginationSearchSortRequestDTO requestDTO) {
    return VsResponseUtil.ok(productService.getProductsByServiceId(serviceId, requestDTO));
  }

  @Operation(summary = "API create service")
  @PostMapping(value = UrlConstant.Service.CREATE_SERVICE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> createServiceById(@Valid @ModelAttribute ServiceCreateDTO serviceCreateDTO) {
    return VsResponseUtil.ok(hotelService.createService(serviceCreateDTO));
  }

  @Operation(summary = "API update service by id")
  @PutMapping(value = UrlConstant.Service.UPDATE_SERVICE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> updateServiceById(@PathVariable Long serviceId, @Valid @ModelAttribute ServiceUpdateDTO serviceUpdateDTO) {
    return VsResponseUtil.ok(hotelService.updateService(serviceId, serviceUpdateDTO));
  }

  @Operation(summary = "API delete service by id")
  @DeleteMapping(UrlConstant.Service.DELETE_SERVICE)
  public ResponseEntity<?> deleteProductById(@PathVariable Long serviceId) {
    return VsResponseUtil.ok(hotelService.deleteService(serviceId));
  }

}
