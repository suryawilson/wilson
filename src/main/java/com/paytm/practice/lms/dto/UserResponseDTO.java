package com.paytm.practice.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.paytm.practice.lms.enums.TransactionStatus;
import com.paytm.practice.lms.enums.ServiceError;
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
public class UserResponseDTO {

  private Integer code;
  private ErrorResponseDto error;
  private TransactionStatus status;
  private UserResponse content;

  @Autowired
  private CreateUserServiceImpl createUserServiceImpl;

  public UserResponseDTO buildErrorResponse(UserRequestDTO userRequestDTO) {
    if (userRequestDTO.getContactNo() == null) {
      return getUserPostResponseDTO(ServiceError.REQUIRED_CONTACT_NO.getStatus(),
          ServiceError.REQUIRED_CONTACT_NO.getMessage());
    } else if (userRequestDTO.getContactNo().length() != 10) {
      return getUserPostResponseDTO(ServiceError.INVALID_CONTACT_NO.getStatus(),
          ServiceError.INVALID_CONTACT_NO.getMessage());
    } else if (createUserServiceImpl.findUserByContact(userRequestDTO)) {
      return getUserPostResponseDTO(ServiceError.USER_WITH_SAME_CONTACT_EXIST.getStatus(),
          ServiceError.USER_WITH_SAME_CONTACT_EXIST.getMessage());
    } else if (userRequestDTO.getName() == null) {
      return getUserPostResponseDTO(ServiceError.REQUIRED_NAME.getStatus(),
          ServiceError.REQUIRED_NAME.getMessage());
    } else if (userRequestDTO.getName().length() <= 3) {
      return getUserPostResponseDTO(ServiceError.INVALID_NAME.getStatus(),
          ServiceError.INVALID_NAME.getMessage());
    } else if (createUserServiceImpl.findUserByName(userRequestDTO)) {
      return getUserPostResponseDTO(ServiceError.USER_WITH_SAME_NAME_EXIST.getStatus(),
          ServiceError.USER_WITH_SAME_NAME_EXIST.getMessage());
    }
    return getUserPostResponseDTO(ServiceError.INTERNAL_SERVER_ERROR.getStatus(),
        ServiceError.INTERNAL_SERVER_ERROR.getMessage());
  }

  private UserResponseDTO getUserPostResponseDTO(String status, String message) {
    return UserResponseDTO.builder()
        .error(
            ErrorResponseDto.builder()
                .status(status)
                .message(message)
                .build())
        .build();
  }
}

