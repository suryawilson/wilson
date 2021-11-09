package com.paytm.practice.lms.dto;

import com.paytm.practice.lms.entity.BorrowDetails;
import com.paytm.practice.lms.enums.ServiceError;
import com.paytm.practice.lms.enums.TransactionStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class HistoryResponseDTO {
  private Integer code;
  private List<BorrowDetails> content;
}
