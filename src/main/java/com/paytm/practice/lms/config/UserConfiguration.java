package com.paytm.practice.lms.config;

import com.paytm.practice.lms.dto.BookResponseDTO;
import com.paytm.practice.lms.dto.BorrowBookResponseDTO;
import com.paytm.practice.lms.dto.LibraryCardResponseDTO;
import com.paytm.practice.lms.dto.ReturnBookResponseDTO;
import com.paytm.practice.lms.dto.UserResponseDTO;
import com.paytm.practice.lms.entity.Book;
import com.paytm.practice.lms.entity.BorrowDetails;
import com.paytm.practice.lms.entity.User;
import com.paytm.practice.lms.service.impl.LibraryCardServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

  @Bean("customBook")
  public Book customBook() {
    return new Book();
  }

  @Bean("customUser")
  public User customUser() {
    return new User();
  }

  @Bean("customBorrowDetails")
  public BorrowDetails customBorrowDetails() {
    return new BorrowDetails();
  }

  @Bean("customReturnBookResponseDTO")
  public ReturnBookResponseDTO customReturnBookResponseDTO() {
    return new ReturnBookResponseDTO();
  }

  @Bean("customUserResponseDTO")
  public UserResponseDTO customUserResponseDTO() {
    return new UserResponseDTO();
  }

  @Bean("customBookResponseDTO")
  public BookResponseDTO customBookResponseDTO() {
    return new BookResponseDTO();
  }

  @Bean("customLibraryCardService")
  public LibraryCardServiceImpl libraryCardService() {
    return new LibraryCardServiceImpl();
  }

  @Bean("customLibraryCardResponseDTO")
  public LibraryCardResponseDTO libraryCardResponseDTO() {
    return new LibraryCardResponseDTO();
  }

  @Bean("customBorrowBookResponseDTO")
  public BorrowBookResponseDTO borrowBookResponseDTO() {
    return new BorrowBookResponseDTO();
  }
}
