package com.bookinghotel.entity;

import com.bookinghotel.entity.common.UserDateAuditing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking extends UserDateAuditing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Instant checkIn;

  private Instant checkOut;

  @Column(nullable = false)
  private Integer status;

  @Nationalized
  private String note;

  //Link to table BookingDetail
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "booking")
  @JsonIgnore
  private Set<BookingDetail> bookingDetails = new HashSet<>();

  //Link to table ServiceDetail
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "booking")
  @JsonIgnore
  private Set<ServiceDetail> serviceDetails = new HashSet<>();

}
