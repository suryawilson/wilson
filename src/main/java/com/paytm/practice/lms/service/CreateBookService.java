package com.paytm.practice.lms.service;

import com.paytm.practice.lms.dto.BookRequestDTO;
import com.paytm.practice.lms.dto.BookResponse;

public interface CreateBookService {

  BookResponse addBook(BookRequestDTO bookRequestDTO);

  boolean findBookByTitle(BookRequestDTO bookRequestDTO);
}
