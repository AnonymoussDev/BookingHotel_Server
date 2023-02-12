package com.bookinghotel.constant;

public class ErrorMessage {

  public static final String UNAUTHORIZED = "Sorry, You're not authorized to access this resource.";
  public static final String INVALID_SOME_THING_FIELD_IS_REQUIRED = "This field is required!";
  public static final String INVALID_SOME_THING_FIELD = "Invalid data!";
  public static final String INVALID_FORMAT_SOME_THING_FIELD = "Invalid format!";
  public static final String NOT_BLANK_FIELD = "Can't be blank!";
  public static final String INVALID_DATE = "Invalid time!";
  public static final String ERR_INVALID_FILE = "Invalid file format!";

  public static class User {
    public static final String ERR_NOT_FOUND_EMAIL_OR_PHONE = "User not found with this email or phone: %s";
    public static final String ERR_NOT_FOUND_ID = "User not found with id: %s";
  }

}
