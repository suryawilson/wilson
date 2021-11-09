package com.paytm.practice.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.paytm.practice.lms.enums.TransactionStatus;
import com.paytm.practice.lms.enums.ServiceError;
import com.paytm.practice.lms.service.CreateBookService;
import com.paytm.practice.lms.service.CreateUserService;
import com.paytm.practice.lms.service.impl.CreateBookServiceImpl;
import com.paytm.practice.lms.service.impl.CreateUserServiceImpl;
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
public class BookResponseDTO {

  private Integer code;
  private ErrorResponseDto error;
  private TransactionStatus status;
  private BookResponse content;

  @Autowired
  private CreateBookService createBookService;
  @Autowired
  private CreateUserService createUserService;

  public BookResponseDTO buildErrorResponse(Long userId, BookRequestDTO bookRequestDTO) {
    if (bookRequestDTO.getTitle() == null) {
      return getBookResponseDTO(ServiceError.REQUIRED_BOOK_TITLE.getStatus(),
          ServiceError.REQUIRED_BOOK_TITLE.getMessage());
    } else if (bookRequestDTO.getAuthorName() == null) {
      return getBookResponseDTO(ServiceError.REQUIRED_BOOK_AUTHOR.getStatus(),
          ServiceError.REQUIRED_BOOK_AUTHOR.getMessage());
    } else if (userId == null) {
      return getBookResponseDTO(ServiceError.USER_ID_REQUIRED.getStatus(),
          ServiceError.USER_ID_REQUIRED.getMessage());
    } else if (!createUserService.findUserById(userId)) {
      return getBookResponseDTO(ServiceError.ONLY_ADMIN_CAN_ADD_BOOK.getStatus(),
          ServiceError.ONLY_ADMIN_CAN_ADD_BOOK.getMessage());
    } else if (createBookService.findBookByTitle(bookRequestDTO)) {
      return getBookResponseDTO(ServiceError.BOOK_WITH_SAME_NAME_EXIST.getStatus(),
          ServiceError.BOOK_WITH_SAME_NAME_EXIST.getMessage());
    }
    return getBookResponseDTO(ServiceError.INTERNAL_SERVER_ERROR.getStatus(),
        ServiceError.INTERNAL_SERVER_ERROR.getMessage());
  }

  private BookResponseDTO getBookResponseDTO(String status, String message) {
    return BookResponseDTO.builder()
        .error(
            ErrorResponseDto.builder()
                .status(status)
                .message(message)
                .build())
        .build();
  }
}
