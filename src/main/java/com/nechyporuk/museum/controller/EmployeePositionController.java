package com.nechyporuk.museum.controller;

import com.nechyporuk.museum.dao.EmployeePositionDao;
import com.nechyporuk.museum.entity.EmployeePosition;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employeePosition")
public class EmployeePositionController {
  private final EmployeePositionDao employeePositionDao;

  public EmployeePositionController(EmployeePositionDao employeePositionDao) {
    this.employeePositionDao = employeePositionDao;
  }

  @PostMapping
  public void save(@RequestBody EmployeePosition employeePosition) {
    employeePositionDao.save(employeePosition);
  }

  @GetMapping
  public List<EmployeePosition> getAll() {
    return employeePositionDao.getAll();
  }

  @PutMapping
  public void update(@RequestBody EmployeePosition employeePosition) {
    employeePositionDao.update(employeePosition);
  }

  @DeleteMapping
  public void delete(@RequestParam Long id) {
    employeePositionDao.delete(id);
  }

  @GetMapping("/one")
  public Optional<EmployeePosition> getOneById(@RequestParam Long id) {
    return employeePositionDao.getOneById(id);
  }
}
