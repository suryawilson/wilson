package com.paytm.practice.lms.helper;

import com.paytm.practice.lms.dto.BookRequestDTO;
import com.paytm.practice.lms.dto.UserRequestDTO;
import com.paytm.practice.lms.entity.Book;
import com.paytm.practice.lms.repository.BookRepo;
import com.paytm.practice.lms.repository.UserRepo;
import com.paytm.practice.lms.service.CreateBookService;
import com.paytm.practice.lms.service.CreateUserService;
import com.paytm.practice.lms.service.SearchBookService;
import com.paytm.practice.lms.service.impl.CreateBookServiceImpl;
import com.paytm.practice.lms.service.impl.CreateUserServiceImpl;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidatorHelper {

  @Autowired
  private CreateBookService createBookService;
  @Autowired
  private CreateUserService createUserService;
  @Autowired
  private SearchBookService searchBookService;

  public boolean validateUserName(UserRequestDTO userRequestDTO) {
    return userRequestDTO.getName() != null
        && userRequestDTO.getName().length() > 3
        && !createUserService.findUserByName(userRequestDTO);
  }

  public boolean validateUserContact(UserRequestDTO userRequestDTO) {
    return userRequestDTO.getContactNo() != null
        && userRequestDTO.getContactNo().length() == 10
        && !createUserService.findUserByContact(userRequestDTO);
  }

  public boolean validateBookDetails(Long userId, BookRequestDTO bookRequestDTO) {
    return bookRequestDTO.getTitle() != null
        && bookRequestDTO.getAuthorName() != null
        && userId != null
        && createUserService.findUserById(userId)
        && !createBookService.findBookByTitle(bookRequestDTO);
  }

  public boolean validateLibraryId(Long userId) {
    return userId != null;
  }

  public boolean validateReturnBookDetails(Long userId, Long borrowId) {
    return userId != null
        && borrowId != null;
  }

  public boolean validateBorrowBookDetails(Long userId, Long isbnCode) {
    Optional<Book> getBook = searchBookService.findById(isbnCode);
    return userId != null
        && isbnCode != null
        && getBook.isPresent()
        && getBook.get().getNumber_of_copies() > 0
        && createUserService.findById(userId).isPresent();
  }
}
