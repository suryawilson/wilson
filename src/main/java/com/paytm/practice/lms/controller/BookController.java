package com.paytm.practice.lms.controller;

import com.paytm.practice.lms.dto.BookResponseDTO;
import com.paytm.practice.lms.dto.BookRequestDTO;
import com.paytm.practice.lms.dto.BookResponse;
import com.paytm.practice.lms.dto.SearchBookResponseDTO;
import com.paytm.practice.lms.entity.Book;
import com.paytm.practice.lms.enums.SearchType;
import com.paytm.practice.lms.helper.ValidatorHelper;
import com.paytm.practice.lms.service.CreateBookService;
import com.paytm.practice.lms.service.SearchBookService;
import java.io.IOException;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/lms/book")
@NoArgsConstructor
public class BookController {

  /**
   * This controller provides the mapping for book related services e.g. adding a new book or search
   * a book in the library
   */

  public CreateBookService createBookService;
  private SearchBookService searchBookService;
  public ValidatorHelper validatorHelper;
  private BookResponseDTO bookResponseDTO;

  @Autowired
  public BookController(
      @Qualifier("customBookResponseDTO") final BookResponseDTO bookResponseDTO,
      final SearchBookService searchBookService,
      final CreateBookService createBookService,
      final ValidatorHelper validatorHelper) {
    this.createBookService = createBookService;
    this.searchBookService = searchBookService;
    this.validatorHelper = validatorHelper;
    this.bookResponseDTO = bookResponseDTO;
  }

  /**
   * This get mapping will be used for searching a book in the library
   */
  @GetMapping(value = "/search")
  public ResponseEntity<Mono<SearchBookResponseDTO>> searchBook(
      @RequestParam(name = "search_type") final SearchType searchType,
      @RequestParam(name = "search_string") final String searchString)
      throws IOException {
    log.info("search a book in the library for searchType {} and searchName {}", searchType,
        searchString);
    try {
      List<Book> availableBooks = searchBookService.searchAvailableBooks(searchType, searchString);
      log.info("available books with searchType {} and searchName {} are : {}",
          searchType, searchString, availableBooks);
      if (availableBooks.isEmpty()) {
        return ResponseEntity.ok(null);
      } else {
        return ResponseEntity.ok(Mono.just(SearchBookResponseDTO.builder()
            .code(200)
            .content(availableBooks)
            .build()));
      }
    } catch (Exception e) {
      log.error("exception occurred while searching a book {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  /**
   * This post mapping will be used for adding a book to the library
   */
  @PostMapping(value = "/add", consumes = {"application/json"})
  public ResponseEntity<Mono<BookResponseDTO>> addBook(
      final @RequestParam(value = "user_id") Long userId,
      final @NonNull @RequestBody BookRequestDTO bookRequestDTO)
      throws IOException {
    log.info("add a book in the library by userId {} with book details {}",
        userId, bookRequestDTO);
    if (!validatorHelper.validateBookDetails(userId, bookRequestDTO)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Mono.just(bookResponseDTO.buildErrorResponse(userId, bookRequestDTO)));
    }
    try {
      BookResponse bookResponse = createBookService.addBook(bookRequestDTO);
      log.info("book is added to the library by userId {} with book details {}",
          userId, bookResponse);
      if (bookResponse == null) {
        return ResponseEntity.ok(null);
      } else {
        return ResponseEntity.ok(Mono.just(BookResponseDTO.builder()
            .code(200)
            .content(bookResponse)
            .build()));
      }
    } catch (Exception e) {
      log.error("exception occurred while adding a book to the library {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }
}
