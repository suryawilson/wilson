package com.paytm.practice.lms.service.impl;

import com.paytm.practice.lms.entity.Book;
import com.paytm.practice.lms.enums.SearchType;
import com.paytm.practice.lms.repository.BookRepo;
import com.paytm.practice.lms.service.SearchBookService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SearchBookServiceImpl implements SearchBookService {

  private BookRepo bookRepo;
  private Book book;

  @Autowired
  public SearchBookServiceImpl(@Qualifier("customBook") final Book book,
      final BookRepo bookRepo) {
    this.bookRepo = bookRepo;
    this.book = book;
  }

  @Override
  public Optional<Book> findById(Long id) {
    return bookRepo.findById(id);
  }

  @Override
  public List<Book> searchAvailableBooks(SearchType searchType, String searchString) {
   switch(searchType){
     case TITLE:
       return bookRepo.findByTitleStartingWith(searchString);
     case AUTHOR:
       //return bookRepo.findByAuthorStartingWith(searchString);
     case SUBJECT:
       return bookRepo.findBySubjectStartingWith(searchString);
    }
    return null;
  }
}
