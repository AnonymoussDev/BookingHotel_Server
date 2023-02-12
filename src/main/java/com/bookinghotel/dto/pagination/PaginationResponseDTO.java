package com.bookinghotel.dto.pagination;

import com.bookinghotel.dto.common.CommonResponseDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class PaginationResponseDTO<T> extends CommonResponseDTO {

  private PagingMeta meta;

  private List<T> items;

  public List<T> getContent() {
    return items == null ? null : new ArrayList<>(items);
  }

  public final void setContent(List<T> data) {
    if (data == null) {
      this.items = null;
    } else {
      this.items = Collections.unmodifiableList(data);
    }
  }

}
