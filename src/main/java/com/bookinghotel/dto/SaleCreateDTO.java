package com.bookinghotel.dto;

import com.bookinghotel.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaleCreateDTO {

  @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  private Instant dayStart;

  @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  private Instant dayEnd;

  @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  private Integer salePercent;

}
