package com.bookinghotel.dto.pagination;

import com.bookinghotel.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PaginationRequestDTO {

  private Integer pageNum = CommonConstant.ZERO_INT_VALUE;

  private Integer pageSize = CommonConstant.ZERO_INT_VALUE;

  public int getPageNum() {
    if (pageNum < 1) {
      pageNum = CommonConstant.ONE_INT_VALUE;
    }
    return pageNum - 1;
  }

  public int getPageSize() {
    if (pageSize < 1) {
      pageSize = CommonConstant.PAGE_SIZE_DEFAULT;
    }
    return pageSize;
  }

}
