package com.paytm.practice.lms.service.impl;

import com.paytm.practice.lms.entity.BorrowDetails;
import com.paytm.practice.lms.repository.BorrowRepo;
import com.paytm.practice.lms.repository.UserRepo;
import com.paytm.practice.lms.service.UserHistoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserHistoryServiceImpl implements UserHistoryService {

  @Autowired UserRepo userRepo;
  @Autowired private BorrowRepo borrowRepo;

  @Override
  public List<BorrowDetails> getUserHistory(Long userId) {
    return borrowRepo.findAllByUser(userId);
  }
}
