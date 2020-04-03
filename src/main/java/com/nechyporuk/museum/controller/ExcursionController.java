package com.nechyporuk.museum.controller;

import com.nechyporuk.museum.dao.ExcursionDao;
import com.nechyporuk.museum.entity.Excursion;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/excursion")
public class ExcursionController {
  private final ExcursionDao excursionDao;

  public ExcursionController(ExcursionDao excursionDao) {
    this.excursionDao = excursionDao;
  }

  @PostMapping
  public void save(@RequestBody Excursion excursion) {
    excursionDao.save(excursion);
  }

  @GetMapping
  public List<Excursion> getAll() {
    return excursionDao.getAll();
  }

  @GetMapping("/one")
  public Optional<Excursion> getOneById(@RequestParam Long id) {
    return excursionDao.getOneById(id);
  }

  @PutMapping
  public void update(@RequestBody Excursion excursion) {
    excursionDao.update(excursion);
  }

  @DeleteMapping
  public void delete(@RequestParam Long id) {
    excursionDao.delete(id);
  }
}
