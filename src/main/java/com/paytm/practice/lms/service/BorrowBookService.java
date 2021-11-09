package com.paytm.practice.lms.service;

import com.paytm.practice.lms.dto.BorrowBookResponse;
import com.paytm.practice.lms.dto.ReturnBookResponse;

public interface BorrowBookService {

  BorrowBookResponse borrowBook(Long userId, Long isbnCode);
  ReturnBookResponse returnBook( Long userId, Long borrowId);
}
