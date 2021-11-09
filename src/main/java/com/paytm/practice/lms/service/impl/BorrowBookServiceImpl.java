package com.paytm.practice.lms.service.impl;

import com.paytm.practice.lms.dto.BorrowBookResponse;
import com.paytm.practice.lms.dto.ReturnBookResponse;
import com.paytm.practice.lms.entity.Book;
import com.paytm.practice.lms.entity.BorrowDetails;
import com.paytm.practice.lms.repository.BookRepo;
import com.paytm.practice.lms.repository.BorrowRepo;
import com.paytm.practice.lms.service.BorrowBookService;
import java.time.Duration;
import java.time.LocalDate;
import java.util.concurrent.locks.Lock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class BorrowBookServiceImpl implements BorrowBookService {

  private BorrowRepo borrowRepo;
  private BookRepo bookRepo;
  private Book book;
  private BorrowDetails borrowDetails;

  @Autowired
  public BorrowBookServiceImpl(@Qualifier("customBorrowDetails") final BorrowDetails borrowDetails,
      final BorrowRepo borrowRepo,
      final Book book,
      final BookRepo bookRepo) {
    this.bookRepo = bookRepo;
    this.book = book;
    this.borrowRepo = borrowRepo;
    this.borrowDetails = borrowDetails;
  }

  @Transactional
  @Override
  public BorrowBookResponse borrowBook(Long userId, Long isbnCode) {
    Book getBook = bookRepo.findById(isbnCode).get();
    BorrowDetails borrowDetails = new BorrowDetails();
    borrowDetails.setDueDate(LocalDate.now().plusDays(30));
    borrowDetails.setCheckoutDate(LocalDate.now());
    borrowDetails.setUser(userId);
    borrowDetails.setIsbnCode(isbnCode);
    borrowRepo.save(borrowDetails);

    getBook.setNumber_of_copies(getBook.getNumber_of_copies() - 1);
    bookRepo.save(getBook);

    return BorrowBookResponse.builder()
        .dueDate(borrowDetails.getDueDate())
        .barcode(Long.valueOf(1))
        .message("Book Issued")
        .build();
  }

  @Override
  public ReturnBookResponse returnBook(Long userId, Long borrowId) {

    BorrowDetails borrowDetails = borrowRepo.findById(borrowId).get();
    Long fine = new Long(0);
    if (LocalDate.now().isAfter(borrowDetails.getDueDate())) {
      Duration diff = Duration.between(LocalDate.now(), borrowDetails.getDueDate());
      fine = diff.toDays() * 2;
    }
    borrowDetails.setFine(fine);
    borrowDetails.setReturnDate(LocalDate.now());
    borrowRepo.save(borrowDetails);

    Book getBook = bookRepo.findById(borrowDetails.getIsbnCode()).get();
    getBook.setNumber_of_copies(getBook.getNumber_of_copies() + 1);
    bookRepo.save(getBook);

    return ReturnBookResponse.builder()
        .issueId(borrowId)
        .fine(fine)
        .message("Book Return")
        .build();
  }
}
