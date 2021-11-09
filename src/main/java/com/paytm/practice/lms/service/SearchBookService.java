package com.paytm.practice.lms.service;

import com.paytm.practice.lms.dto.BookResponse;
import com.paytm.practice.lms.entity.Book;
import com.paytm.practice.lms.enums.SearchType;
import java.util.List;
import java.util.Optional;

public interface SearchBookService {
  Optional<Book> findById(Long id);
  List<Book> searchAvailableBooks(SearchType searchType, String searchString);
}
