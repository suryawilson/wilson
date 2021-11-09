package com.paytm.practice.lms.entity;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "borrow_details")
public class BorrowDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "checkout_date", nullable = false)
  private LocalDate checkoutDate;

  @Column(name = "due_date")
  private LocalDate dueDate;

  @Column(name = "return_date")
  private LocalDate returnDate;

  @Column(name = "user")
  private Long user;

  @Column(name = "isbn_code")
  private Long isbnCode;

  @Column(name = "fine")
  private Long fine;

  @Column(name = "paid")
  private Long paid;
}
