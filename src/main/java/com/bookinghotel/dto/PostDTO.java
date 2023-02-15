package com.bookinghotel.dto;

import com.bookinghotel.dto.common.CreatedByDTO;
import com.bookinghotel.dto.common.DateAuditingDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO extends DateAuditingDTO {

  private Long id;

  private String content;

  private CreatedByDTO createdBy;

  private List<MediaDTO> medias = new LinkedList<>();

}
