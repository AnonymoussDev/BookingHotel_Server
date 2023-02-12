package com.bookinghotel;

import com.bookinghotel.constant.RoleEnum;
import com.bookinghotel.entity.Role;
import com.bookinghotel.entity.User;
import com.bookinghotel.repository.RoleRepository;
import com.bookinghotel.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BookingHotelApplication {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  public BookingHotelApplication(UserRepository userRepository, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(BookingHotelApplication.class, args);
  }

  @Bean
  CommandLineRunner init() {
    return args -> {
      PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

      if (roleRepository.count() == 0) {
        roleRepository.save(new Role(null, RoleEnum.ADMIN.getValue(), "Administrators", null));
        roleRepository.save(new Role(null, RoleEnum.USER.getValue(), "Customer", null));
      }

      if (userRepository.count() == 0) {
        User admin = new User(null, "bloomi.hit@gmail.com", "0387753709",
            passwordEncoder.encode("admin"), "Admin", "Admin", "Nam", "2001-1-1",
            "Hà Nội", Boolean.TRUE, roleRepository.findByRoleName(RoleEnum.ADMIN.getValue()), null);
        userRepository.save(admin);
      }
    };
  }
}
