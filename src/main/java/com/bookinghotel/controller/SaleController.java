package com.bookinghotel.controller;

import com.bookinghotel.base.RestApiV1;
import com.bookinghotel.base.VsResponseUtil;
import com.bookinghotel.constant.UrlConstant;
import com.bookinghotel.dto.SaleCreateDTO;
import com.bookinghotel.dto.SaleUpdateDTO;
import com.bookinghotel.dto.pagination.PaginationSearchSortRequestDTO;
import com.bookinghotel.service.SaleService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class SaleController {

  private final SaleService saleService;

  @ApiOperation("API get sale by id")
  @GetMapping(UrlConstant.Sale.GET_SALE)
  public ResponseEntity<?> getSaleById(@PathVariable Long saleId) {
    return VsResponseUtil.ok(saleService.getSale(saleId));
  }

  @ApiOperation("API get all sale")
  @GetMapping(UrlConstant.Sale.GET_SALES)
  public ResponseEntity<?> getSales(@Valid PaginationSearchSortRequestDTO requestDTO) {
    return VsResponseUtil.ok(saleService.getSales(requestDTO));
  }

  @ApiOperation("API create sale")
  @PostMapping(UrlConstant.Sale.CREATE_SALE)
  public ResponseEntity<?> updateSaleById(@Valid SaleCreateDTO createDTO) {
    return VsResponseUtil.ok(saleService.createSale(createDTO));
  }

  @ApiOperation("API update sale by id")
  @PutMapping(UrlConstant.Sale.UPDATE_SALE)
  public ResponseEntity<?> updateSaleById(@PathVariable Long saleId, @Valid SaleUpdateDTO updateDTO) {
    return VsResponseUtil.ok(saleService.updateSale(saleId, updateDTO));
  }

}
