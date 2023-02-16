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
        case "lastModifiedDate":
          return "last_modified_date";
        default:
          return "created_date";
      }
    }
  },
  POST {
    @Override
    public String getSortBy(String sortBy) {
      switch (sortBy) {
        case "lastModifiedDate":
          return "last_modified_date";
        default:
          return "created_date";
      }
    }
  },
  PRODUCT {
    @Override
    public String getSortBy(String sortBy) {
      switch (sortBy) {
        case "price":
          return "price";
        case "lastModifiedDate":
          return "last_modified_date";
        default:
          return "created_date";
      }
    }
  },
  SALE {
    @Override
    public String getSortBy(String sortBy) {
      switch (sortBy) {
        case "dayStart":
          return "day_start";
        case "dayEnd":
          return "day_end";
        case "salePercent":
          return "sale_percent";
        case "lastModifiedDate":
          return "last_modified_date";
        default:
          return "created_date";
      }
    }
  },
}
