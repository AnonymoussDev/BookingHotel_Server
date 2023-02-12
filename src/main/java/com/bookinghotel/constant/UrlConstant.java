package com.bookinghotel.constant;

public class UrlConstant {

  public static class Auth {
    private static final String PRE_FIX = "/auth";

    public static final String LOGIN = PRE_FIX + "/login";

    public static final String SIGNUP = PRE_FIX + "/signup";
    public static final String VERIFY_SIGNUP = SIGNUP + "/verify";

    public static final String FORGOT_PASS = PRE_FIX + "/forgot-password";
    public static final String VERIFY_FORGOT_PASS = FORGOT_PASS + "/verify";
    public static final String CONFIRM_FORGOT_PASS = FORGOT_PASS + "/confirm";

    private Auth() {
    }

  }

}
