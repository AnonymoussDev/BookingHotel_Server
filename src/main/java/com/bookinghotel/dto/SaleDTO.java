package com.bookinghotel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaleDTO {

  private Long id;

  private Instant dayStart;

  private Instant dayEnd;

  private Integer salePercent;

}
