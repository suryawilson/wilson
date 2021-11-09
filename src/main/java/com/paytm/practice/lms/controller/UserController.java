package com.paytm.practice.lms.controller;

import com.paytm.practice.lms.dto.BorrowBookResponse;
import com.paytm.practice.lms.dto.BorrowBookResponseDTO;
import com.paytm.practice.lms.dto.HistoryResponseDTO;
import com.paytm.practice.lms.dto.LibraryCardResponse;
import com.paytm.practice.lms.dto.LibraryCardResponseDTO;
import com.paytm.practice.lms.dto.ReturnBookResponse;
import com.paytm.practice.lms.dto.ReturnBookResponseDTO;
import com.paytm.practice.lms.dto.UserResponseDTO;
import com.paytm.practice.lms.dto.UserResponse;
import com.paytm.practice.lms.dto.UserRequestDTO;
import com.paytm.practice.lms.entity.BorrowDetails;
import com.paytm.practice.lms.helper.ValidatorHelper;
import com.paytm.practice.lms.service.BorrowBookService;
import com.paytm.practice.lms.service.CreateUserService;
import com.paytm.practice.lms.service.LibraryCardService;
import com.paytm.practice.lms.service.UserHistoryService;
import java.io.IOException;
import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/lms/user")
public class UserController {

  /**
   * This controller provides the mapping for user related services e.g. adding a new user or get
   * the library card etc
   */

  public CreateUserService createUserService;
  public LibraryCardService libraryCardService;
  public BorrowBookService borrowBookService;
  public UserHistoryService userHistoryService;
  public ValidatorHelper validatorHelper;
  public UserResponseDTO userResponseDTO;
  public LibraryCardResponseDTO libraryCardResponseDTO;
  public BorrowBookResponseDTO borrowBookResponseDTO;
  public ReturnBookResponseDTO returnBookResponseDTO;

  @Autowired
  public UserController(
      @Qualifier("customUserResponseDTO") final UserResponseDTO userResponseDTO,
      @Qualifier("customLibraryCardService") final LibraryCardService libraryCardService,
      @Qualifier("customBorrowBookResponseDTO") final BorrowBookResponseDTO borrowBookResponseDTO,
      @Qualifier("customLibraryCardResponseDTO") final LibraryCardResponseDTO libraryCardResponseDTO,
      @Qualifier("customReturnBookResponseDTO") final ReturnBookResponseDTO returnBookResponseDTO,
      final CreateUserService createUserService,
      final ValidatorHelper validatorHelper,
      final UserHistoryService userHistoryService,
      final BorrowBookService borrowBookService) {
    this.validatorHelper = validatorHelper;
    this.createUserService = createUserService;
    this.userResponseDTO = userResponseDTO;
    this.libraryCardService = libraryCardService;
    this.borrowBookService = borrowBookService;
    this.userHistoryService = userHistoryService;
    this.libraryCardResponseDTO = libraryCardResponseDTO;
    this.borrowBookResponseDTO = borrowBookResponseDTO;
    this.returnBookResponseDTO = returnBookResponseDTO;
  }

  /**
   * This post mapping will be used for adding a new user to the library
   */

  @PostMapping(value = "/add", consumes = {"application/json"})
  public ResponseEntity<Mono<UserResponseDTO>> addUser(
      final @NonNull @RequestBody UserRequestDTO userRequestDTO)
      throws IOException {
    log.info("add a new user to the library with following details {}", userRequestDTO);
    if (!validatorHelper.validateUserName(userRequestDTO)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Mono.just(userResponseDTO.buildErrorResponse(userRequestDTO)));
    }
    if (!validatorHelper.validateUserContact(userRequestDTO)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Mono.just(userResponseDTO.buildErrorResponse(userRequestDTO)));
    }
    try {
      UserResponse userResponse = createUserService.addUser(userRequestDTO);
      log.info("user {} added to the library", userRequestDTO);
      if (userResponse == null) {
        return ResponseEntity.ok(null);
      } else {
        return ResponseEntity.ok(Mono.just(UserResponseDTO.builder()
            .code(200)
            .content(userResponse)
            .build()));
      }
    } catch (Exception e) {
      log.error("exception occurred while adding new user {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

  }

  /**
   * This post mapping issue a book to the user
   */
  @PostMapping(value = "/borrow")
  public ResponseEntity<Mono<BorrowBookResponseDTO>> borrowBook(
      final @RequestParam(value = "user_id") Long userId,
      final @RequestParam(value = "isbn_code") Long isbnCode)
      throws IOException {
    if (!validatorHelper.validateBorrowBookDetails(userId, isbnCode)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Mono.just(borrowBookResponseDTO.buildErrorResponse(userId, isbnCode)));
    }
    try {
      BorrowBookResponse borrowBookResponse = borrowBookService.borrowBook(userId, isbnCode);
      log.info("user id {} borrowed a book with following details {}", userId, borrowBookResponse);
      if (borrowBookResponse == null) {
        return ResponseEntity.ok(null);
      } else {
        return ResponseEntity.ok(Mono.just(BorrowBookResponseDTO.builder()
            .code(200)
            .content(borrowBookResponse)
            .build()));
      }
    } catch (Exception e) {
      log.error("exception occurred while borrowing a book", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  /**
   * This put mapping will update details of fines, book availability on book return
   */
  @PutMapping(value = "/return")
  public ResponseEntity<Mono<ReturnBookResponseDTO>> returnBook(
      final @RequestParam(value = "user_id") Long userId,
      final @RequestParam(value = "issue_id") Long borrowId)
      throws IOException {
    log.info("user id {} returned the book with issue id {}", userId, borrowId);
    if (!validatorHelper.validateReturnBookDetails(userId, borrowId)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Mono.just(returnBookResponseDTO.buildErrorResponse(userId, borrowId)));
    }
    try {
      ReturnBookResponse returnBookResponse = borrowBookService.returnBook(userId, borrowId);
      log.info("user id {} return the book with details {}", userId, returnBookResponse);
      if (returnBookResponse == null) {
        return ResponseEntity.ok(null);
      } else {
        return ResponseEntity.ok(Mono.just(ReturnBookResponseDTO.builder()
            .code(200)
            .content(returnBookResponse)
            .build()));
      }
    } catch (Exception e) {
      log.error("exception occurred while returning a book", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  /**
   * This get mapping will give active book details issued to the user
   */
  @GetMapping(value = "/get_library_card")
  public ResponseEntity<Mono<LibraryCardResponseDTO>> getLibraryCard(
      final @RequestParam(value = "user_id") Long userId)
      throws IOException {
    log.info("user id {} requested for library card", userId);
    if (!validatorHelper.validateLibraryId(userId)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Mono.just(libraryCardResponseDTO.buildErrorResponse(userId)));
    }
    try {
      LibraryCardResponse libraryCardResponse = libraryCardService.getLibraryCard(userId);
      log.info("user id {} has library card details {}", userId, libraryCardResponse);
      if (libraryCardResponse == null) {
        return ResponseEntity.ok(null);
      } else {
        return ResponseEntity.ok(Mono.just(LibraryCardResponseDTO.builder()
            .code(200)
            .content(libraryCardResponse)
            .build()));
      }
    } catch (Exception e) {
      log.error("exception occurred while fetching library card", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  /**
   * This get mapping will be used for user book history
   */
  @GetMapping(value = "/history")
  public ResponseEntity<Mono<HistoryResponseDTO>> getHistory(
      final @RequestParam(value = "user_id") Long userId)
      throws IOException {
    log.info("user id {} requested for his history", userId);
    try {
      List<BorrowDetails> userHistory = userHistoryService.getUserHistory(userId);
      log.info("user id {} has has history {}", userId, userHistory);
      if (userHistory.isEmpty()) {
        return ResponseEntity.ok(null);
      } else {
        return ResponseEntity.ok(Mono.just(HistoryResponseDTO.builder()
            .code(200)
            .content(userHistory)
            .build()));
      }
    } catch (Exception e) {
      log.error("exception occurred while fetching user history", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }
}
