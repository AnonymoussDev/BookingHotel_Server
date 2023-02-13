package com.bookinghotel.entity.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
public abstract class UserDateAuditing extends DateAuditing {

  @CreatedBy
  @Column(updatable = false)
  private Long createdBy;

  @LastModifiedBy
  @Column(nullable = false)
  private Long lastModifiedBy;

  @Column(nullable = false, columnDefinition = "boolean default false")
  private Boolean deleteFlag;

}
