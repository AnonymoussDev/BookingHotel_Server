package com.bookinghotel.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public abstract class DateAuditingDTO {

  private Instant createdDate;

  private Instant lastModifiedDate;

}
