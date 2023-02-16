package com.bookinghotel.constant;

public class UrlConstant {

  public static class Auth {
    private static final String PRE_FIX = "/auth";

    public static final String LOGIN = PRE_FIX + "/login";

    public static final String SIGNUP = PRE_FIX + "/signup";
    public static final String VERIFY_SIGNUP = SIGNUP + "/verify";

    public static final String FORGOT_PASS = PRE_FIX + "/forgot-password";
    public static final String VERIFY_FORGOT_PASS = FORGOT_PASS + "/verify";

    private Auth() {
    }

  }

  public static class Room {
    private static final String PRE_FIX = "/room";

    public static final String GET_ROOMS = PRE_FIX;
    public static final String GET_ROOM = PRE_FIX + "/{roomId}";

    public static final String CREATE_ROOM = PRE_FIX + "/create";

    public static final String UPDATE_ROOM = PRE_FIX + "/update/{roomId}";

    public static final String DELETE_ROOM = PRE_FIX + "/delete/{roomId}";

    private Room() {
    }
  }

  public static class Sale {
    private static final String PRE_FIX = "/sale";

    public static final String GET_SALES = PRE_FIX;
    public static final String GET_SALE = PRE_FIX + "/{saleId}";

    public static final String CREATE_SALE = PRE_FIX + "/create";

    public static final String UPDATE_SALE = PRE_FIX + "/update/{saleId}";

    public static final String DELETE_SALE = PRE_FIX + "/delete/{saleId}";

    private Sale() {
    }
  }

  public static class Post {
    private static final String PRE_FIX = "/post";

    public static final String GET_POSTS = PRE_FIX;
    public static final String GET_POST = PRE_FIX + "/{postId}";

    public static final String CREATE_POST = PRE_FIX + "/create";

    public static final String UPDATE_POST = PRE_FIX + "/update/{postId}";

    public static final String DELETE_POST = PRE_FIX + "/delete/{postId}";

    private Post() {
    }
  }

  public static class Product {
    private static final String PRE_FIX = "/product";

    public static final String GET_PRODUCTS = PRE_FIX;
    public static final String GET_PRODUCT = PRE_FIX + "/{productId}";

    public static final String CREATE_PRODUCT = PRE_FIX + "/create";

    public static final String UPDATE_PRODUCT = PRE_FIX + "/update/{productId}";

    public static final String DELETE_PRODUCTS = PRE_FIX + "/delete/{productId}";

    private Product() {
    }
  }

}
