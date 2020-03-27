package com.nechyporuk.museum.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@NoArgsConstructor

@Entity
@Table(name = "exhibition")
public class Exhibition {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "name")
  private String name;
  @Column(name = "creation_date")
  private Date creationDate;
  @Column(name = "description")
  private String description;
  //Author
  //Hall
}
