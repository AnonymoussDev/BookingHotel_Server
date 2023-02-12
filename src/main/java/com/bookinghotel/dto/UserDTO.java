package com.bookinghotel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

  private Long id;

  private String email;

  private String phoneNumber;

  private String firstName;

  private String lastName;

  private String gender;

  private String birthday;

  private String address;

  private Boolean status;

  private String roleName;

}
