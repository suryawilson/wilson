package com.paytm.practice.lms.enums;

import lombok.Getter;
import com.google.common.base.Enums;

@Getter
public enum ServiceError {
  USER_WITH_SAME_NAME_EXIST("CONFLICT", "User with same name already exists"),
  INVALID_NAME("NOT_ACCEPTABLE", "Name is not valid"),
  REQUIRED_NAME("NOT_ACCEPTABLE", "Name must be provided"),
  USER_WITH_SAME_CONTACT_EXIST("CONFLICT", "User with same contact number already exists"),
  INVALID_CONTACT_NO("NOT_ACCEPTABLE", "Contact Number is not valid"),
  REQUIRED_CONTACT_NO("NOT_ACCEPTABLE", "Contact Number must be provided"),
  REQUIRED_BOOK_TITLE("NOT_ACCEPTABLE", "Book must have a title"),
  REQUIRED_BOOK_AUTHOR("NOT_ACCEPTABLE", "Book must have an author"),
  ONLY_ADMIN_CAN_ADD_BOOK("UNAUTHORIZED", "Only Admins can add books"),
  BOOK_WITH_SAME_NAME_EXIST("CONFLICT", "Book already exists"),
  USER_ID_REQUIRED("NOT_ACCEPTABLE", "User id is required"),
  BOOK_ID_REQUIRED("NOT_ACCEPTABLE", "ISBN code is required"),
  USER_NOT_FOUND("NOT_FOUND", "User doesn't exist"),
  BOOK_NOT_FOUND("NOT_FOUND", "Book doesn't exist"),
  BOOK_OUT_OF_STOCK("CONFLICT", "All copies of the book are borrowed"),
  REQUIRED_BARCODE("NOT_ACCEPTABLE", "Barcode is required"),
  REQUIRED_LIBRARY_ID("NOT_ACCEPTABLE", "Library Id is required"),
  REQUIRED_ISSUE_ID("NOT_ACCEPTABLE", "Issue Id is required"),
  INTERNAL_SERVER_ERROR("FAILED", "Internal server error");

  private String status;
  private String message;

  ServiceError(final String status, final String message) {
    this.status = status;
    this.message = message;
  }

  public static ServiceError getEnum(final String name) {
    return Enums.getIfPresent(ServiceError.class, name)
        .or(ServiceError.INTERNAL_SERVER_ERROR);
  }
}
