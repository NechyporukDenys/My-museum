package com.nechyporuk.museum.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@NoArgsConstructor

@Entity
@Table(name = "author")
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "first_name")
  @NotBlank
  private String firstName;
  @Column(name = "last_name")
  @NotBlank
  private String lastName;
  @Column(name = "born_date")
  @NotNull
  private Date bornDate;
  @Column(name = "death_date")
  private Date deathDate;
}