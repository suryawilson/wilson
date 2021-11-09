package com.paytm.practice.lms.repository;


import com.paytm.practice.lms.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

  Optional<User> findById(final String userId);
  User findByName(final String name);
  User findByContactNo(final String contactNo);
}
