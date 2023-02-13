package com.bookinghotel.exception;

import com.bookinghotel.base.RestData;
import com.bookinghotel.base.VsResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

  //Error validate for param
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
    List<String> errors = new ArrayList<>();
    ex.getConstraintViolations().forEach(cv -> errors.add(cv.getMessage()));
    Map<String, List<String>> result = new HashMap<>();
    result.put("errors", errors);
    return VsResponseUtil.error(HttpStatus.BAD_REQUEST, result);
  }

  //Error validate for body
  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<?> handleValidException(BindException ex, WebRequest req) {
    Map<String, String> result = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      result.put(fieldName, errorMessage);
    });
    return VsResponseUtil.error(HttpStatus.BAD_REQUEST, result);
  }

  @ExceptionHandler(DuplicateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<RestData<?>> handleDuplicateException(DuplicateException ex, WebRequest req) {
    return VsResponseUtil.error(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler({AccessDeniedException.class, ForbiddenException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseEntity<RestData<?>> handleAccessDeniedException(Exception ex, WebRequest req) {
    return VsResponseUtil.error(HttpStatus.FORBIDDEN, ex.getMessage());
  }

  @ExceptionHandler(InternalServerException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<RestData<?>> handlerInternalServerException(InternalServerException ex, WebRequest req) {
    return VsResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
  }

  @ExceptionHandler(InvalidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<RestData<?>> handlerInvalidException(InvalidException ex, WebRequest req) {
    return VsResponseUtil.error(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<RestData<?>> handlerNotFoundException(NotFoundException ex, WebRequest req) {
    return VsResponseUtil.error(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<RestData<?>> handleUnauthorizedException(UnauthorizedException ex, WebRequest req) {
    return VsResponseUtil.error(HttpStatus.UNAUTHORIZED, ex.getMessage());
  }

  @ExceptionHandler(UploadImageException.class)
  @ResponseStatus(HttpStatus.BAD_GATEWAY)
  public ResponseEntity<RestData<?>> handleUploadImageException(UploadImageException ex, WebRequest req) {
    return VsResponseUtil.error(HttpStatus.BAD_GATEWAY, ex.getMessage());
  }

}