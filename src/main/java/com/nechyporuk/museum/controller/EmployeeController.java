package com.nechyporuk.museum.controller;

import com.nechyporuk.museum.dao.EmployeeDao;
import com.nechyporuk.museum.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
  @Autowired
  private EmployeeDao employeeDao;

  @GetMapping("/one")
  public Optional<Employee> getOneById(@RequestParam Long id) {
    return employeeDao.getOneById(id);
  }

  @PutMapping
  public void update(@RequestBody Employee employee) {
    employeeDao.update(employee);
  }

  @DeleteMapping
  public void delete(@RequestParam Long id) {
    employeeDao.delete(id);
  }

  @PostMapping
  public void save(@RequestBody Employee employee) {
    employeeDao.save(employee);
  }

  @GetMapping
  public List<Employee> getAll() {
    return employeeDao.getAll();
  }
}
