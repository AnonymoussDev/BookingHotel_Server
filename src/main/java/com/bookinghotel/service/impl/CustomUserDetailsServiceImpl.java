package com.bookinghotel.service.impl;

import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.entity.User;
import com.bookinghotel.exception.InvalidException;
import com.bookinghotel.repository.UserRepository;
import com.bookinghotel.security.UserPrincipal;
import com.bookinghotel.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService, CustomUserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String emailOrPhone) throws UsernameNotFoundException {
    User user = userRepository.findByEmailOrPhone(emailOrPhone)
        .orElseThrow(() -> new UsernameNotFoundException(String.format(ErrorMessage.User.ERR_NOT_FOUND_EMAIL_OR_PHONE, emailOrPhone)));
    if(user.getEnabled().equals(Boolean.FALSE)) {
      throw new InvalidException(ErrorMessage.Auth.ERR_ACCOUNT_NOT_ACTIVATED);
    }
    return UserPrincipal.create(user);
  }

  @Override
  @Transactional
  public UserDetails loadUserById(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException(String.format(ErrorMessage.User.ERR_NOT_FOUND_ID, id)));
    if(user.getEnabled().equals(Boolean.FALSE)) {
      throw new InvalidException(ErrorMessage.Auth.ERR_ACCOUNT_NOT_ACTIVATED);
    }
    return UserPrincipal.create(user);
  }

}
