package com.bookinghotel.dto;

import com.bookinghotel.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AuthenticationResponseDTO {

  private String tokenType = CommonConstant.BEARER_TOKEN;

  private String accessToken;

  private String refreshToken;

}
