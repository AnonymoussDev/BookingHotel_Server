package com.bookinghotel.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class VerificationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Type(type = "uuid-char")
  @Column(updatable = false, unique = true, nullable = false, columnDefinition = "CHAR(36)")
  private String token;

  @Column(nullable = false)
  private Date expirationTime;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_TOKEN"))
  private User user;

  public VerificationToken(User user, String token) {
    super();
    this.user = user;
    this.token = token;
    this.expirationTime = calculateExpirationDate();
  }

  public Date calculateExpirationDate() {
    final int EXPIRATION_TIME = 3;
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(new Date().getTime());
    calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
    return new Date(calendar.getTime().getTime());
  }

}
