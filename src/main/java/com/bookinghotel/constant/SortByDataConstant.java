package com.bookinghotel.constant;

public enum SortByDataConstant implements SortByInterface {
  ROOM {
    @Override
    public String getSortBy(String sortBy) {
      switch (sortBy) {
        case "price":
          return "price";
        case "maxNum":
          return "max_num";
        case "floor":
          return "floor";
        default:
          return "created_date";
      }
    }
  },
}
