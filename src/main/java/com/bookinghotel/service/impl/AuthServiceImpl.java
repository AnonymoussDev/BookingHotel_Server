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
import com.bookinghotel.exception.*;
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
import org.springframework.security.authentication.DisabledException;
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
    } catch (DisabledException e) {
      throw new UnauthorizedException(ErrorMessage.Auth.ERR_ACCOUNT_NOT_ENABLED);
    } catch (BadCredentialsException e) {
      throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_AUTHENTICATION);
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
    VerificationToken verificationToken = new VerificationToken(user, token.toString());
    verificationTokenRepository.save(verificationToken);

    //set data mail
    DataMailDTO dataMailDTO = new DataMailDTO();
    dataMailDTO.setTo(userCreateDTO.getEmail());
    dataMailDTO.setSubject(CommonMessage.SUBJECT_REGISTER);
    Map<String, Object> properties = new HashMap<>();
    properties.put("name", userCreateDTO.getLastName() + " " + userCreateDTO.getFirstName());
    properties.put("token", token.toString());

    try {
      sendMail.sendEmailWithHTML(dataMailDTO, CommonMessage.SIGNUP_TEMPLATE, properties);
      return new CommonResponseDTO(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
    } catch (Exception ex) {
      throw new InternalServerException(ErrorMessage.ERR_EXCEPTION_GENERAL);
    }
  }

  @Override
  public CommonResponseDTO verifySignUp(String token) {
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

    //generate uuid token
    UUID token = UUID.randomUUID();
    VerificationToken verificationToken = new VerificationToken(user.get(), token.toString());
    verificationTokenRepository.save(verificationToken);

    //set data mail
    DataMailDTO dataMailDTO = new DataMailDTO();
    dataMailDTO.setTo(user.get().getEmail());
    dataMailDTO.setSubject(CommonMessage.SUBJECT_FORGOT_PASS);
    Map<String, Object> properties = new HashMap<>();
    properties.put("name", user.get().getLastName() + " " + user.get().getFirstName());
    properties.put("token", token.toString());

    try {
      sendMail.sendEmailWithHTML(dataMailDTO, CommonMessage.FORGOT_PASSWORD_TEMPLATE, properties);
      return new CommonResponseDTO(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
    } catch (Exception ex) {
      throw new InternalServerException(ErrorMessage.ERR_EXCEPTION_GENERAL);
    }
  }

  @Override
  public CommonResponseDTO verifyForgotPassword(String email, String token, String newPassword) {
    Optional<User> user = userRepository.findByEmail(email);
    checkNotFoundUserByEmail(user, email);
    VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
    checkVerificationToken(verificationToken);

    checkAccountNotEqualTokenVerify(user.get(), verificationToken);
    if (passwordEncoder.matches(newPassword, user.get().getPassword())) {
      throw new DuplicateException(ErrorMessage.Auth.ERR_DUPLICATE_PASSWORD);
    }

    try {
      user.get().setPassword(passwordEncoder.encode(newPassword));
      userRepository.save(user.get());
      verificationTokenRepository.delete(verificationToken);
      return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.FORGOT_PASSWORD_SUCCESS);
    } catch (Exception ex) {
      throw new InternalServerException(ErrorMessage.ERR_EXCEPTION_GENERAL);
    }
  }

  private void checkVerificationToken(VerificationToken verificationToken) {
    if (verificationToken == null) {
      throw new InvalidException(ErrorMessage.Auth.INVALID_TOKEN);
    } else {
      Calendar cal = Calendar.getInstance();
      if ((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
        throw new InvalidException(ErrorMessage.Auth.EXPIRED_TOKEN);
      }
    }
  }

  private void checkAccountNotEqualTokenVerify(User user, VerificationToken token) {
    if(!Objects.equals(user.getId(), token.getUser().getId())) {
      throw new InvalidException(ErrorMessage.Auth.INCORRECT_TOKEN);
    }
  }

  private void checkNotFoundUserByEmail(Optional<User> user, String email) {
    if (user.isEmpty()) {
      throw new NotFoundException(String.format(ErrorMessage.User.ERR_ACCOUNT_NOT_FOUND_BY_EMAIL, email));
    }
  }

}
