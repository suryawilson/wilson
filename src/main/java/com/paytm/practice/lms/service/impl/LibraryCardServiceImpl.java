package com.paytm.practice.lms.service.impl;

import com.paytm.practice.lms.dto.LibraryCardResponse;
import com.paytm.practice.lms.entity.BorrowDetails;
import com.paytm.practice.lms.entity.User;
import com.paytm.practice.lms.repository.BorrowRepo;
import com.paytm.practice.lms.repository.UserRepo;
import com.paytm.practice.lms.service.LibraryCardService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LibraryCardServiceImpl implements LibraryCardService {

  @Autowired UserRepo userRepo;
  @Autowired private BorrowRepo borrowRepo;

  @Override
  public LibraryCardResponse getLibraryCard(Long userId) {
    User user = userRepo.findById(userId).get();
    List<BorrowDetails> borrowDetails = borrowRepo.findAllByUserAndReturnDateIsNull(userId);

    return LibraryCardResponse.builder()
        .libraryId(Long.valueOf(12))
        .name(user.getName())
        .contactNo(user.getContactNo())
        .list_of_active_borrows(borrowDetails)
        .build();
  }
}
