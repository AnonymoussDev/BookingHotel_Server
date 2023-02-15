package com.bookinghotel.repository;

import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.entity.User;
import com.bookinghotel.exception.NotFoundException;
import com.bookinghotel.security.UserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT u FROM User u WHERE u.id = ?1 AND u.enabled = true")
  Optional<User> findById(Long id);

  @Query("SELECT u FROM User u WHERE u.email = ?1 AND u.enabled = true")
  Optional<User> findByEmail(String email);

  @Query("SELECT (COUNT(u) > 0) FROM User u WHERE u.email = ?1 AND u.enabled = true")
  Boolean existsByEmail(String email);

  @Query("SELECT u FROM User u WHERE u.email = ?1 OR u.phoneNumber = ?1 AND u.enabled = true")
  Optional<User> findByEmailOrPhone(String emailOrPhone);

  default User getUser(UserPrincipal currentUser) {
    return findByEmail(currentUser.getUsername())
        .orElseThrow(() -> new NotFoundException(
            String.format(ErrorMessage.User.ERR_ACCOUNT_NOT_FOUND_BY_EMAIL, currentUser.getUsername())
        ));
  }

}
