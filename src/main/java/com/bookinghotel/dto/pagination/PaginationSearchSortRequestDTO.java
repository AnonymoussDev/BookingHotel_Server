package com.bookinghotel.dto.pagination;

import com.bookinghotel.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaginationSearchSortRequestDTO extends PaginationSortRequestDTO {

  private String keyword = CommonConstant.EMPTY_STRING;

}
