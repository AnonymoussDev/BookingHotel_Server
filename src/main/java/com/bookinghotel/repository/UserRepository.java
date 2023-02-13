package com.bookinghotel.repository;

import com.bookinghotel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT u FROM User u WHERE u.id = ?1 AND u.status = true")
  Optional<User> findById(Long id);

  @Query("SELECT u FROM User u WHERE u.email = ?1 AND u.status = true")
  Optional<User> findByEmail(String email);

  @Query("SELECT (COUNT(u) > 0) FROM User u WHERE u.email = ?1")
  Boolean existsByEmail(String email);

  @Query("SELECT u FROM User u WHERE u.email = ?1 OR u.phoneNumber = ?1 AND u.status = true")
  Optional<User> findByEmailOrPhone(String emailOrPhone);

}
