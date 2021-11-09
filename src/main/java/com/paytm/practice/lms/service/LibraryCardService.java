package com.paytm.practice.lms.service;

import com.paytm.practice.lms.dto.LibraryCardResponse;

public interface LibraryCardService {
  LibraryCardResponse getLibraryCard(Long userId);
}
