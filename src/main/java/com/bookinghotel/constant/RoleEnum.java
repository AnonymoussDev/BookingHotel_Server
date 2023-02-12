package com.bookinghotel.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum RoleEnum {

  ADMIN("ROLE_ADMIN"),
  USER("ROLE_USER");

  private String value;

}
