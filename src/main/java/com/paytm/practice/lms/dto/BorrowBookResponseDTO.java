package com.paytm.practice.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.paytm.practice.lms.entity.Book;
import com.paytm.practice.lms.enums.ServiceError;
import com.paytm.practice.lms.enums.TransactionStatus;
import com.paytm.practice.lms.repository.BookRepo;
import com.paytm.practice.lms.repository.UserRepo;
import com.paytm.practice.lms.service.CreateUserService;
import com.paytm.practice.lms.service.SearchBookService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class BorrowBookResponseDTO {

  private Integer code;
  private ErrorResponseDto error;
  private TransactionStatus status;
  private BorrowBookResponse content;

  @Autowired
  private SearchBookService searchBookService;
  @Autowired
  private CreateUserService createUserService;

  public BorrowBookResponseDTO buildErrorResponse(Long userId, Long isbnCode) {
    Optional<Book> getBook = searchBookService.findById(isbnCode);
    if (userId == null) {
      return getBorrowBookResponseDTO(ServiceError.USER_ID_REQUIRED.getStatus(),
          ServiceError.USER_ID_REQUIRED.getMessage());
    } else if (isbnCode == null) {
      return getBorrowBookResponseDTO(ServiceError.BOOK_ID_REQUIRED.getStatus(),
          ServiceError.BOOK_ID_REQUIRED.getMessage());
    } else if (!createUserService.findById(userId).isPresent()) {
      return getBorrowBookResponseDTO(ServiceError.USER_NOT_FOUND.getStatus(),
          ServiceError.USER_NOT_FOUND.getMessage());
    } else if (!getBook.isPresent()) {
      return getBorrowBookResponseDTO(ServiceError.BOOK_NOT_FOUND.getStatus(),
          ServiceError.BOOK_NOT_FOUND.getMessage());
    } else if (getBook.get().getNumber_of_copies() <= 0) {
      return getBorrowBookResponseDTO(ServiceError.BOOK_OUT_OF_STOCK.getStatus(),
          ServiceError.BOOK_OUT_OF_STOCK.getMessage());
    }
    return getBorrowBookResponseDTO(ServiceError.INTERNAL_SERVER_ERROR.getStatus(),
        ServiceError.INTERNAL_SERVER_ERROR.getMessage());
  }

  private BorrowBookResponseDTO getBorrowBookResponseDTO(String status, String message) {
    return BorrowBookResponseDTO.builder()
        .error(
            ErrorResponseDto.builder()
                .status(status)
                .message(message)
                .build())
        .build();
  }
}
