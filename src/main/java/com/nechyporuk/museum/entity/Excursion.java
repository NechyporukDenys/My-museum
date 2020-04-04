package com.nechyporuk.museum.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@NoArgsConstructor

@Entity
@Table(name = "excursion")
public class Excursion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "name")
  private String name;
  @Column(name = "program")
  private String program;
  @Column(name = "start_time")
  private String startTime;
  @Column(name = "end_time")
  private String endTime;
  @Column(name = "schedule")
  private String schedule;
  @Column(name = "end_date")
  private Date endDate;

  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;
}
