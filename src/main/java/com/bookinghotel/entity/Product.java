package com.bookinghotel.entity;

import com.bookinghotel.entity.common.FlagUserDateAuditing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends FlagUserDateAuditing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String thumbnail;

  @Nationalized
  private String description;

  //Link to table Service
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @JoinColumn(name = "service_id", foreignKey = @ForeignKey(name = "FK_PRODUCT_SERVICE"))
  private Service service;

}
