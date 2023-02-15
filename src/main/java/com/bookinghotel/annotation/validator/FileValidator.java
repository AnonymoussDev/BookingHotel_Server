package com.bookinghotel.annotation.validator;

import com.bookinghotel.annotation.ValidFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class FileValidator implements ConstraintValidator<ValidFile, List<MultipartFile>> {

  @Override
  public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext constraintValidatorContext) {
    if(files != null) {
      for(MultipartFile multipartFile : files) {
        String contentType = multipartFile.getContentType();
        if (contentType != null && !isSupportedContentType(contentType)) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean isSupportedContentType(String contentType) {
    return contentType.equals("image/png")
        || contentType.equals("image/jpg")
        || contentType.equals("image/jpeg");
  }

}
