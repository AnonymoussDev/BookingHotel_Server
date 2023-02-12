package com.bookinghotel.entity.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
public abstract class DateAuditing {

  @CreatedDate
  @Column(name = "created_date", nullable = false, updatable = false)
  private Instant createdDate;

  @LastModifiedDate
  @Column(name = "last_modified_date", nullable = false)
  private Instant lastModifiedDate;

}
