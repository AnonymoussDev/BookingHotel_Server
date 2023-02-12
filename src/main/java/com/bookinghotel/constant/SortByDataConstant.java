package com.bookinghotel.constant;

public enum SortByDataConstant implements SortByInterface {
  ACCOUNT {
    @Override
    public String getSortBy(String sortBy) {
      switch (sortBy) {
        case "key1":
          return "value1";
        case "key2":
          return "value2";
        default:
          return "created_date";
      }
    }
  },
}
