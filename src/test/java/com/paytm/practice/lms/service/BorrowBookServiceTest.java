package com.paytm.practice.lms.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.paytm.practice.lms.dto.BorrowBookResponse;
import com.paytm.practice.lms.entity.Book;
import com.paytm.practice.lms.repository.BookRepo;
import com.paytm.practice.lms.repository.BorrowRepo;
import com.paytm.practice.lms.service.impl.BorrowBookServiceImpl;
import java.io.IOException;
import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class BorrowBookServiceTest {

  @Mock
  private BookRepo bookRepo;
  @Mock
  private BorrowRepo borrowRepo;
  @InjectMocks
  public BorrowBookServiceImpl borrowBookService;


  @Test
  public void test_borrowBook() throws IOException {
    Long userId = new Long(1);
    Long isbn_Code = new Long(1);
    Book book = new Book(isbn_Code, "Fundamentals of Physics", "Eng", "physics", 4);

    when(bookRepo.findById(isbn_Code)).thenReturn(java.util.Optional.of(book));

    BorrowBookResponse response = borrowBookService.borrowBook(userId, isbn_Code);

    assertNotNull(response);
    assertEquals(1, response.getBarcode());
    assertEquals(LocalDate.now().plusDays(30), response.getDueDate());
    assertEquals("Book Issued", response.getMessage());
  }
}
