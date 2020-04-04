package com.nechyporuk.museum.controller;

import com.nechyporuk.museum.dao.HallDao;
import com.nechyporuk.museum.entity.Hall;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hall")
public class HallController {
  private final HallDao hallDao;

  public HallController(HallDao hallDao) {
    this.hallDao = hallDao;
  }

  @PostMapping
  public void save(@RequestBody Hall hall) {
    hallDao.save(hall);
  }

  @GetMapping
  public List<Hall> getAll() {
    return hallDao.getAll();
  }

  @GetMapping("/one")
  public Optional<Hall> getOneById(@RequestParam Long id) {
    return hallDao.getOneById(id);
  }

  @DeleteMapping
  public void delete(@RequestParam Long id) {
    hallDao.delete(id);
  }

  @PutMapping
  public void update(@RequestBody Hall hall) {
    hallDao.update(hall);
  }
}
