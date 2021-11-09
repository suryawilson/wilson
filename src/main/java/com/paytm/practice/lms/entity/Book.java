package com.paytm.practice.lms.entity;

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
@Table(name = "book")
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "isbn_code")
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "language")
  private String language;

  @Column(name = "subject")
  private String subject;

  @Column(name = "number_of_copies")
  private Integer number_of_copies;
}
