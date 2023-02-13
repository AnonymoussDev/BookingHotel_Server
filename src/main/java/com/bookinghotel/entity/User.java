package com.bookinghotel.entity;

import com.bookinghotel.entity.common.DateAuditing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends DateAuditing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String phoneNumber;

  @Column(nullable = false)
  @JsonIgnore
  private String password;

  @Nationalized
  @Column(nullable = false)
  private String firstName;

  @Nationalized
  @Column(nullable = false)
  private String lastName;

  @Nationalized
  @Column(nullable = false)
  private String gender;

  @Column(nullable = false)
  private String birthday;

  @Nationalized
  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private Boolean enabled;

  //Link to table Role
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "FK_USER_ROLE"))
  private Role role;

  //Link to table Post
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  @JsonIgnore
  private Set<Post> posts = new HashSet<>();

  @PrePersist
  public void prePersist() {
    if (this.enabled == null) {
      this.enabled = Boolean.FALSE;
    }
  }

}
