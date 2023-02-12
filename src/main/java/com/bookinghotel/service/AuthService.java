package com.bookinghotel.service;

import com.bookinghotel.dto.AuthenticationRequestDTO;
import com.bookinghotel.dto.AuthenticationResponseDTO;
import com.bookinghotel.dto.UserCreateDTO;
import com.bookinghotel.dto.common.CommonResponseDTO;

import java.util.UUID;

public interface AuthService {

  AuthenticationResponseDTO login(AuthenticationRequestDTO request);

  CommonResponseDTO signUp(UserCreateDTO userCreateDTO);

  CommonResponseDTO verifySignUp(UUID token);

  CommonResponseDTO forgotPassword(String email);

  CommonResponseDTO verifyPasswordResetToken(UUID token);

  CommonResponseDTO confirmForgotPassword(UUID token, String newPassword);

}
