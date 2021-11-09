package com.paytm.practice.lms.service;

import com.paytm.practice.lms.dto.UserRequestDTO;
import com.paytm.practice.lms.dto.UserResponse;
import com.paytm.practice.lms.entity.User;
import java.util.Optional;

public interface CreateUserService {

  UserResponse addUser(UserRequestDTO userRequestDTO);

  boolean findUserByName(UserRequestDTO userRequestDTO);

  boolean findUserByContact(UserRequestDTO userRequestDTO);

  boolean findUserById(Long userId);

  Optional<User> findById(Long userId);
}
