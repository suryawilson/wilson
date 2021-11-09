package com.paytm.practice.lms.service.impl;

import com.paytm.practice.lms.dto.UserRequestDTO;
import com.paytm.practice.lms.dto.UserResponse;
import com.paytm.practice.lms.entity.User;
import com.paytm.practice.lms.repository.UserRepo;
import com.paytm.practice.lms.service.CreateUserService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateUserServiceImpl implements CreateUserService {

  private UserRepo userRepo;
  private User user;

  @Autowired
  public CreateUserServiceImpl(@Qualifier("customUser") final User user,
      final UserRepo userRepo) {
    this.user = user;
    this.userRepo = userRepo;
  }

  @Override
  public UserResponse addUser(UserRequestDTO userRequestDTO) {
    User user = new User();
    user.setName(userRequestDTO.getName());
    user.setContactNo(userRequestDTO.getContactNo());
    user.setPassword(userRequestDTO.getPassword());
    user.setAdmin(userRequestDTO.getIsAdmin());

    userRepo.save(user);

    return UserResponse.builder()
    .name(userRequestDTO.getName())
    .contact_number(userRequestDTO.getContactNo())
    .library_id(new Long(12))
    .message("user data save successful ")
    .build();

  }

  @Override
  public boolean findUserByName(UserRequestDTO userRequestDTO) {
    return (userRepo.findByName(userRequestDTO.getName()) != null) ? true : false;
  }

  @Override
  public boolean findUserByContact(UserRequestDTO userRequestDTO) {
    return (userRepo.findByContactNo(userRequestDTO.getContactNo()) != null) ? true : false;
  }

  @Override
  public boolean findUserById(Long userId) {
    return (userRepo.findById(userId).get().isAdmin()) ? true : false;
  }

  @Override
  public Optional<User> findById(Long userId) {
    return userRepo.findById(userId);
  }


}
