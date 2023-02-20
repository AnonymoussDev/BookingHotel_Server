package com.bookinghotel.entity;

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
@Table(name = "products")
public class Service extends FlagUserDateAuditing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String thumbnail;

  @Column(nullable = false)
  private Integer price;

  @Column(nullable = false)
  @Lob
  @Nationalized
  private String description;

  //Link to table Service
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "service")
  @JsonIgnore
  private Set<Product> products = new HashSet<>();

}
