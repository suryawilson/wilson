package com.paytm.practice.lms.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
public class BookControllerTest {

  @Mock
  public CreateBookService createBookService;
  @Mock
  public SearchBookService searchBookService;
  @Mock
  public ValidatorHelper validatorHelper;

  @InjectMocks
  public BookController bookController;

  @Test
  public void test_addBook_api() throws IOException {
    BookRequestDTO bookRequestDTO = new BookRequestDTO(
        1, "Fundamentals of Physics", "Eng", "physics", "SChand", 4);
    BookResponse bookResponse = new BookResponse(new Long(12), "Fundamentals of Physics", "physics",
        "SChand", "book added");

    when(createBookService.addBook(bookRequestDTO)).thenReturn(bookResponse);
    when(validatorHelper.validateBookDetails(new Long(1), bookRequestDTO)).thenReturn(true);
    ResponseEntity<Mono<BookResponseDTO>> response = bookController.addBook(new Long(1),
        bookRequestDTO);

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    response.getBody().subscribe(res -> {
      assertEquals(res.getCode(), 200);
      assertEquals(res.getContent().getTitle(), bookRequestDTO.getTitle());
      assertEquals(res.getContent().getAuthor_name(), bookRequestDTO.getAuthorName());
      assertEquals(res.getContent().getGenre(), bookRequestDTO.getSubject());
      assertEquals(res.getContent().getLibrary_id(), new Long(12));
    });
  }

  @Test
  public void test_searchBook_api() throws IOException {
    Book book = new Book(new Long(1), "Fundamentals of Physics", "Eng", "physics", 4);
    when(searchBookService.searchAvailableBooks(SearchType.TITLE, "a")).thenReturn(
        Arrays.asList(book));
    ResponseEntity<Mono<SearchBookResponseDTO>> response = bookController.searchBook(
        SearchType.TITLE, "a");

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    response.getBody().subscribe(res -> {
      assertEquals(res.getCode(), 200);
      assertEquals(res.getContent().get(0).getTitle(), book.getTitle());
      assertEquals(res.getContent().get(0).getLanguage(), book.getLanguage());
      assertEquals(res.getContent().get(0).getSubject(), book.getSubject());
      assertEquals(res.getContent().get(0).getNumber_of_copies(), book.getNumber_of_copies());
    });
  }
}
