package com.nechyporuk.museum.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor

@Entity
@Table(name = "hall")
public class Hall {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "name")
  private String name;
  @Column(name = "style")
  private String style;

  @ManyToMany(mappedBy = "halls")
  @JsonIgnore
  private List<Employee> employees;
}
