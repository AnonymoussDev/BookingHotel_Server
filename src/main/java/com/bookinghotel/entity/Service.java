package com.bookinghotel.entity;

import com.bookinghotel.entity.common.UserDateAuditing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "servies")
public class Service extends UserDateAuditing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long price;

  @Column(nullable = false)
  private Integer amount;

  //Link to table Booking
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @JoinColumn(name = "booking_id", foreignKey = @ForeignKey(name = "FK_SERVICE_BOOKING"))
  private Booking booking;

  //Link to table Product
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "FK_SERVICE_PRODUCT"))
  private Product product;

}
