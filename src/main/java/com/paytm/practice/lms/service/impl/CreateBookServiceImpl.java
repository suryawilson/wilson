package com.paytm.practice.lms.service.impl;

import com.paytm.practice.lms.dto.BookRequestDTO;
import com.paytm.practice.lms.dto.BookResponse;
import com.paytm.practice.lms.entity.Book;
import com.paytm.practice.lms.repository.BookRepo;
import com.paytm.practice.lms.service.CreateBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CreateBookServiceImpl implements CreateBookService {

  private BookRepo bookRepo;
  private Book book;

  @Autowired
  public CreateBookServiceImpl(@Qualifier("customBook") final Book book,
      final BookRepo bookRepo) {
    this.bookRepo = bookRepo;
    this.book = book;
  }

  @Transactional
  @Override
  public BookResponse addBook(BookRequestDTO bookRequestDTO) {
    Book book = new Book();
    book.setNumber_of_copies(bookRequestDTO.getNumber_of_copies());
    book.setLanguage(bookRequestDTO.getLanguage());
    book.setTitle(bookRequestDTO.getTitle());
    book.setSubject(bookRequestDTO.getSubject());
    bookRepo.save(book);
    return BookResponse.builder()
        .title(bookRequestDTO.getTitle())
        .author_name(bookRequestDTO.getAuthorName())
        .genre(bookRequestDTO.getSubject())
        .message("book data save successful ")
        .build();
  }

  @Override
  public boolean findBookByTitle(BookRequestDTO bookRequestDTO) {
    return (bookRepo.findByTitle(bookRequestDTO.getTitle()) != null) ? true : false;
  }
}
