package com.bookinghotel.constant;

public class ErrorMessage {

  public static final String UNAUTHORIZED = "Sorry, You're not authorized to access this resource";
  public static final String ERR_EXCEPTION_GENERAL = "Something went wrong, please try again later";

  //error validation dto
  public static final String INVALID_SOME_THING_FIELD_IS_REQUIRED = "This field is required";
  public static final String INVALID_SOME_THING_FIELD = "Invalid data";
  public static final String INVALID_FORMAT_PASSWORD = "Unsatisfactory password";
  public static final String NOT_BLANK_FIELD = "Can't be blank";
  public static final String ERR_INVALID_FILE = "Invalid file format";
  public static final String INVALID_DATE = "Invalid time";
  public static final String INVALID_FORMAT_SOME_THING_FIELD = "Invalid format";

  public static class Auth {

    public static final String ERR_INCORRECT_AUTHENTICATION = "Username or password incorrect";
    public static final String ERR_DUPLICATE_EMAIL = "Email is already taken";
    public static final String ERR_DUPLICATE_PASSWORD = "The new password must be different from the old password";
    public static final String ERR_ACCOUNT_NOT_ENABLED = "This account is not enabled";

    //error token verification
    public static final String INVALID_TOKEN = "Invalid token";
    public static final String EXPIRED_TOKEN = "Token has expired";
    public static final String INCORRECT_TOKEN = "Incorrect token";
  }

  public static class User {
    public static final String ERR_NOT_FOUND_EMAIL_OR_PHONE = "User not found with this email or phone: %s";
    public static final String ERR_NOT_FOUND_ID = "User not found with id: %s";
    public static final String ERR_ACCOUNT_NOT_FOUND_BY_EMAIL = "User not found with email: %s";
  }

  public static class Room {
    public static final String ERR_NOT_FOUND_ID = "Room not found with id: %s";
    public static final String ERR_NO_PHOTO = "Room needs a photo to describe";
  }

  public static class Sale {
    public static final String ERR_NOT_FOUND_ID = "Sale not found with id: %s";
  }

  public static class Post {
    public static final String ERR_NOT_FOUND_ID = "Post not found with id: %s";
  }

  public static class Product {
    public static final String ERR_NOT_FOUND_ID = "Product not found with id: %s";
  }

  public static class Service {
    public static final String ERR_NOT_FOUND_ID = "Service not found with id: %s";
  }

}
