package com.bookinghotel.dto;

import com.bookinghotel.annotation.ValidFile;
import com.bookinghotel.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCreateDTO {

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  private String name;

  @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  @ValidFile
  private MultipartFile thumbnailFile;

  private String description;

  private Long serviceId;

}
