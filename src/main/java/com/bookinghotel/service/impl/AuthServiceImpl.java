package com.bookinghotel.service.impl;

import com.bookinghotel.constant.CommonConstant;
import com.bookinghotel.constant.CommonMessage;
import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.constant.RoleEnum;
import com.bookinghotel.dto.AuthenticationRequestDTO;
import com.bookinghotel.dto.AuthenticationResponseDTO;
import com.bookinghotel.dto.UserCreateDTO;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.dto.common.DataMailDTO;
import com.bookinghotel.entity.User;
import com.bookinghotel.entity.VerificationToken;
import com.bookinghotel.exception.DuplicateException;
import com.bookinghotel.exception.InternalServerException;
import com.bookinghotel.exception.InvalidException;
import com.bookinghotel.exception.NotFoundException;
import com.bookinghotel.mapper.UserMapper;
import com.bookinghotel.repository.RoleRepository;
import com.bookinghotel.repository.UserRepository;
import com.bookinghotel.repository.VerificationTokenRepository;
import com.bookinghotel.security.JwtTokenProvider;
import com.bookinghotel.service.AuthService;
import com.bookinghotel.util.SendMailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final VerificationTokenRepository verificationTokenRepository;

  private final AuthenticationManager authenticationManager;

  private final PasswordEncoder passwordEncoder;

  private final UserMapper userMapper;

  private final JwtTokenProvider jwtTokenProvider;

  private final SendMailUtil sendMail;

  @Override
  public AuthenticationResponseDTO login(AuthenticationRequestDTO request) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getEmailOrPhone(), request.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);

      String jwt = jwtTokenProvider.generateToken(authentication);
      return new AuthenticationResponseDTO(CommonConstant.BEARER_TOKEN, jwt, null);
    } catch (BadCredentialsException e) {
      throw new InvalidException(ErrorMessage.Auth.ERR_INCORRECT_AUTHENTICATION);
    }
  }

  @Override
  public CommonResponseDTO signUp(UserCreateDTO userCreateDTO) {
    if (Boolean.TRUE.equals(userRepository.existsByEmail(userCreateDTO.getEmail()))) {
      throw new DuplicateException(ErrorMessage.Auth.ERR_DUPLICATE_EMAIL);
    }
    //create user
    User user = userMapper.toUser(userCreateDTO);
    user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
    user.setRole(roleRepository.findByRoleName(RoleEnum.USER.getValue()));
    userRepository.save(user);

    //generate uuid token
    UUID token = UUID.randomUUID();
    VerificationToken verificationToken = new VerificationToken(user, token);
    verificationTokenRepository.save(verificationToken);

    //set data mail
    DataMailDTO dataMailDTO = new DataMailDTO();
    dataMailDTO.setTo(userCreateDTO.getEmail());
    dataMailDTO.setSubject(CommonMessage.SUBJECT_REGISTER);
    Map<String, Object> properties = new HashMap<>();
    properties.put("name", userCreateDTO.getLastName() + " " + userCreateDTO.getFirstName());
    properties.put("token", token);

    try {
      sendMail.sendEmailWithHTML(dataMailDTO, CommonMessage.SIGNUP_TEMPLATE, properties);
      return new CommonResponseDTO(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
    } catch (Exception ex) {
      throw new InternalServerException(ErrorMessage.ERR_EXCEPTION_GENERAL);
    }
  }

  @Override
  public CommonResponseDTO verifySignUp(UUID token) {
    VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
    checkVerificationToken(verificationToken);
    try {
      User user = verificationToken.getUser();
      user.setEnabled(CommonConstant.TRUE);
      userRepository.save(user);
      verificationTokenRepository.delete(verificationToken);
      return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.SIGNUP_SUCCESS);
    } catch (Exception e) {
      throw new InternalServerException(ErrorMessage.ERR_EXCEPTION_GENERAL);
    }
  }

  @Override
  public CommonResponseDTO forgotPassword(String email) {
    Optional<User> user = userRepository.findByEmail(email);
    checkNotFoundUserByEmail(user, email);
    checkAccountNotActivated(user.get());

    //generate uuid token
    UUID token = UUID.randomUUID();
    VerificationToken verificationToken = new VerificationToken(user.get(), token);
    verificationTokenRepository.save(verificationToken);

    //set data mail
    DataMailDTO dataMailDTO = new DataMailDTO();
    dataMailDTO.setTo(user.get().getEmail());
    dataMailDTO.setSubject(CommonMessage.SUBJECT_FORGOT_PASS);
    Map<String, Object> properties = new HashMap<>();
    properties.put("name", user.get().getLastName() + " " + user.get().getFirstName());
    properties.put("token", token);

    try {
      sendMail.sendEmailWithHTML(dataMailDTO, CommonMessage.FORGOT_PASSWORD_TEMPLATE, properties);
      return new CommonResponseDTO(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
    } catch (Exception ex) {
      throw new InternalServerException(ErrorMessage.ERR_EXCEPTION_GENERAL);
    }
  }

  @Override
  public CommonResponseDTO verifyPasswordResetToken(UUID token) {
    VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
    checkVerificationToken(verificationToken);
    return new CommonResponseDTO(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  @Override
  public CommonResponseDTO confirmForgotPassword(UUID token, String newPassword) {
    VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
    checkVerificationToken(verificationToken);

    User user = verificationToken.getUser();
    if (passwordEncoder.matches(newPassword, user.getPassword())) {
      throw new DuplicateException(ErrorMessage.Auth.ERR_DUPLICATE_PASSWORD);
    }

    try {
      user.setPassword(passwordEncoder.encode(newPassword));
      userRepository.save(user);
      return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.FORGOT_PASSWORD_SUCCESS);
    } catch (Exception ex) {
      throw new InternalServerException(ErrorMessage.ERR_EXCEPTION_GENERAL);
    }
  }

  private void checkVerificationToken(VerificationToken verificationToken) {
    if (verificationToken == null) {
      throw new InvalidException(ErrorMessage.INVALID_TOKEN);
    } else {
      Calendar cal = Calendar.getInstance();
      if ((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
        throw new InvalidException(ErrorMessage.EXPIRED_TOKEN);
      }
    }
  }

  private void checkAccountNotActivated(User user) {
    if(user.getEnabled().equals(Boolean.FALSE)) {
      throw new InvalidException(ErrorMessage.Auth.ERR_ACCOUNT_NOT_ACTIVATED);
    }
  }

  private void checkNotFoundUserByEmail(Optional<User> user, String email) {
    if (user.isEmpty()) {
      throw new NotFoundException(String.format(ErrorMessage.User.ERR_ACCOUNT_NOT_FOUND_BY_EMAIL, email));
    }
  }

}
