package com.paytm.practice.lms.service;

import com.paytm.practice.lms.dto.LibraryCardResponse;
import com.paytm.practice.lms.entity.BorrowDetails;
import java.util.List;

public interface UserHistoryService {

  List<BorrowDetails> getUserHistory(Long userId);
}
