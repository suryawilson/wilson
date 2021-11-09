package com.paytm.practice.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.paytm.practice.lms.enums.ServiceError;
import com.paytm.practice.lms.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class LibraryCardResponseDTO {

  private Integer code;
  private ErrorResponseDto error;
  private TransactionStatus status;
  private LibraryCardResponse content;

  public LibraryCardResponseDTO buildErrorResponse(Long userId) {
    if (userId == null) {
      return LibraryCardResponseDTO.builder()
          .error(
              ErrorResponseDto.builder()
                  .status(ServiceError.REQUIRED_CONTACT_NO.getStatus())
                  .message(ServiceError.REQUIRED_CONTACT_NO.getMessage())
                  .build())
          .build();
    }
    return LibraryCardResponseDTO.builder()
        .error(
            ErrorResponseDto.builder()
                .status(ServiceError.INTERNAL_SERVER_ERROR.getStatus())
                .message(ServiceError.INTERNAL_SERVER_ERROR.getMessage())
                .build())
        .build();
  }
}
