package com.bookinghotel.dto.pagination;

import com.bookinghotel.constant.CommonConstant;
import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.constant.SortByDataConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaginationSortRequestDTO extends PaginationRequestDTO {

  private String sortBy = CommonConstant.EMPTY_STRING;

  @Pattern(regexp = "^(ASC)|(DESC)$", message = ErrorMessage.INVALID_SOME_THING_FIELD)
  private String sortType = CommonConstant.SORT_TYPE_DESC;

  public String getSortBy(SortByDataConstant constant) {
    return constant.getSortBy(sortBy);
  }
}
