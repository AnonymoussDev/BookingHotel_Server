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
@Table(name = "booking_details")
public class BookingDetail extends UserDateAuditing {

  @EmbeddedId
  BookingDetailId id;

  //Link to table Booking
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @MapsId("bookingId")
  @JoinColumn(name = "booking_id", foreignKey = @ForeignKey(name = "FK_BOOKING_DETAIL_BOOKING"))
  private Booking booking;

  //Link to table Room
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @MapsId("roomId")
  @JoinColumn(name = "room_id", foreignKey = @ForeignKey(name = "FK_BOOKING_DETAIL_ROOM"))
  private Room room;

  @Column(nullable = false)
  private Long price;

}
