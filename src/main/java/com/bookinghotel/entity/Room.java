package com.bookinghotel.entity;

import com.bookinghotel.constant.RoomType;
import com.bookinghotel.entity.common.FlagUserDateAuditing;
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
public class Room extends FlagUserDateAuditing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Nationalized
  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private Long price;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private RoomType type;

  @Column(nullable = false)
  private Integer maxNum;

  @Column(nullable = false)
  private Integer floor;

  @Column(nullable = false)
  private Boolean status;

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

  @PrePersist
  public void prePersist() {
    if (this.status == null) {
      this.status = Boolean.FALSE;
    }
  }

}
