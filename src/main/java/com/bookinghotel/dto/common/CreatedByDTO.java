package com.bookinghotel.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatedByDTO {

  private Long id;

  private String firstName;

  private String lastName;

}
