package com.paytm.practice.lms.repository;

import com.paytm.practice.lms.entity.BorrowDetails;
import com.paytm.practice.lms.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRepo extends JpaRepository<BorrowDetails, Long> {

  List<BorrowDetails> findAllByUserAndReturnDateIsNull(Long userId);
  List<BorrowDetails> findAllByUser(Long userId);

}
