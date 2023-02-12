package com.bookinghotel.entity;

import com.bookinghotel.entity.common.UserDateAuditing;
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
@Table(name = "rooms")
public class Room extends UserDateAuditing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Nationalized
  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private Long price;

  @Column(nullable = false)
  private Integer type;

  @Column(nullable = false)
  private Integer maxNum;

  @Column(nullable = false)
  private Integer floor;

  @Lob
  @Nationalized
  @Column(nullable = false)
  private String description;

  //Link to table Sale
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @JoinColumn(name = "sale_id", foreignKey = @ForeignKey(name = "FK_ROOM_SALE"))
  private Sale sale;

  //Link to table Media
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "room")
  @JsonIgnore
  private Set<Media> medias = new HashSet<>();

  //Link to table BookingDetail
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "room")
  @JsonIgnore
  private Set<BookingDetail> bookingDetails = new HashSet<>();

}
