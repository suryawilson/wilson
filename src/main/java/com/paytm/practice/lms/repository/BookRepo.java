package com.paytm.practice.lms.repository;

import com.paytm.practice.lms.entity.Book;
import com.paytm.practice.lms.entity.User;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

  @Lock(LockModeType.PESSIMISTIC_READ)
  Optional<Book> findById(final Long id);
  Book findByTitle(final String title);
  List<Book> findByTitleStartingWith(String searchString);
  List<Book> findBySubjectStartingWith(String searchString);

}
