package com.nechyporuk.museum.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor

@Entity
@Table(name = "employee")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "employee_hall",
          joinColumns = @JoinColumn(name = "employee_id"),
          inverseJoinColumns = @JoinColumn(name = "hall_id"))
  private List<Hall> halls;

  @ManyToOne
  @JoinColumn(name = "employee_position_id")
  private EmployeePosition employeePosition;

}
