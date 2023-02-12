package com.bookinghotel.controller;

import com.bookinghotel.base.RestApiV1;
import com.bookinghotel.base.VsResponseUtil;
import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.constant.UrlConstant;
import com.bookinghotel.dto.AuthenticationRequestDTO;
import com.bookinghotel.dto.UserCreateDTO;
import com.bookinghotel.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@RequiredArgsConstructor
@Validated
@RestApiV1
public class AuthController {

  private final AuthService authService;

  @ApiOperation("API Login")
  @PostMapping(UrlConstant.Auth.LOGIN)
  public ResponseEntity<?> login(@Valid @RequestBody AuthenticationRequestDTO request) {
    return VsResponseUtil.ok(authService.login(request));
  }

  @ApiOperation("(1) API SignUp and send mail token")
  @PostMapping(UrlConstant.Auth.SIGNUP)
  public ResponseEntity<?> signUp(@Valid @RequestBody UserCreateDTO userCreateDTO) {
    return VsResponseUtil.ok(authService.signUp(userCreateDTO));
  }

  @ApiOperation("(2) API verify signup")
  @PostMapping(UrlConstant.Auth.VERIFY_SIGNUP)
  public ResponseEntity<?> FormVerificationTokenSignUp(@RequestParam("token") UUID token) {
    return VsResponseUtil.ok(authService.verifySignUp(token));
  }

  @ApiOperation("(1) API forgot password")
  @PostMapping(UrlConstant.Auth.FORGOT_PASS)
  public ResponseEntity<?> forgotPassword(@Email(message = ErrorMessage.INVALID_FORMAT_SOME_THING_FIELD)
                                          @RequestParam("email") String email) {
    return VsResponseUtil.ok(authService.forgotPassword(email));
  }

  @ApiOperation("(2) API verify token forgot password")
  @PostMapping(UrlConstant.Auth.VERIFY_FORGOT_PASS)
  public ResponseEntity<?> verifyTokenResetPass(@RequestParam(name = "token") UUID token) {
    return VsResponseUtil.ok(authService.verifyPasswordResetToken(token));
  }

  @ApiOperation("(3) API confirm forgot password")
  @PostMapping(UrlConstant.Auth.CONFIRM_FORGOT_PASS)
  public ResponseEntity<?> forgotPassword(@RequestParam(name = "token") UUID token,
                                          @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$",
                                              message = ErrorMessage.INVALID_FORMAT_PASSWORD)
                                          @RequestParam(name = "password") String newPassword) {
    return VsResponseUtil.ok(authService.confirmForgotPassword(token, newPassword));
  }

}
