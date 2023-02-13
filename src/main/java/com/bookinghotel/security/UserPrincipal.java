package com.bookinghotel.security;

import com.bookinghotel.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserPrincipal implements UserDetails {

  private static final long serialVersionUID = 1L;

  private final Long id;

  private final String firstName;

  private final String lastName;

  @JsonIgnore
  private final String email;

  @JsonIgnore
  private final String phone;

  @JsonIgnore
  private String password;

  private final boolean enabled;

  private final Collection<? extends GrantedAuthority> authorities;

  public UserPrincipal(Long id, String firstName, String lastName, String email, String phone, String password,
                       boolean enabled, Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.password = password;
    this.enabled = enabled;

    if (authorities == null) {
      this.authorities = null;
    } else {
      this.authorities = new ArrayList<>(authorities);
    }
  }

  public static UserPrincipal create(User user) {
    List<GrantedAuthority> authorities = new LinkedList<>();
    authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));

    return new UserPrincipal(user.getId(), user.getFirstName(), user.getLastName(),
        user.getEmail(), user.getPhoneNumber(), user.getPassword(), user.getEnabled(), authorities);
  }

  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPhone() {
    return phone;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities == null ? null : new ArrayList<>(authorities);
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public boolean equals(Object object) {
    if (this == object)
      return true;
    if (object == null || getClass() != object.getClass())
      return false;
    UserPrincipal that = (UserPrincipal) object;
    return Objects.equals(id, that.id);
  }

  public int hashCode() {
    return Objects.hash(id);
  }

}
