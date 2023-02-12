package com.bookinghotel.dto;

import com.bookinghotel.annotation.DateValid;
import com.bookinghotel.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateDTO {

  @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  @Email(message = ErrorMessage.INVALID_FORMAT_SOME_THING_FIELD)
  private String email;

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  @Pattern(regexp = "^\\d{9,11}$", message = ErrorMessage.INVALID_SOME_THING_FIELD)
  private String phoneNumber;

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$", message = ErrorMessage.INVALID_FORMAT_PASSWORD)
  private String password;

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  private String firstName;

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  private String lastName;

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  @Pattern(regexp = "^(Nam)|(Nữ)$", message = ErrorMessage.INVALID_SOME_THING_FIELD)
  private String gender;

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  @DateValid(message = ErrorMessage.INVALID_DATE)
  private String birthday;

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  private String address;

}
