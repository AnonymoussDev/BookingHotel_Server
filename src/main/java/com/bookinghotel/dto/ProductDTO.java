package com.bookinghotel.dto;

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
public class ProductDTO {

  private Long id;

  private String title;

  private String thumbnail;

  private Integer price;

  private String description;

  private List<MediaDTO> medias = new LinkedList<>();

  private Long categoryId;

}
