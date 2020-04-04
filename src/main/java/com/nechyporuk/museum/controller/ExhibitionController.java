package com.nechyporuk.museum.controller;

import com.nechyporuk.museum.dao.ExhibitionDao;
import com.nechyporuk.museum.entity.Exhibition;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/exhibition")
public class ExhibitionController {
  private final ExhibitionDao exhibitionDao;

  public ExhibitionController(ExhibitionDao exhibitionDao) {
    this.exhibitionDao = exhibitionDao;
  }

  @PostMapping
  public void save(@RequestBody Exhibition exhibition) {
    exhibitionDao.save(exhibition);
  }

  @GetMapping
  public List<Exhibition> getAll() {
    return exhibitionDao.getAll();
  }

  @GetMapping("/one")
  public Optional<Exhibition> getOneById(@RequestParam Long id) {
    return exhibitionDao.getOneById(id);
  }

  @PutMapping
  public void update(@RequestBody Exhibition exhibition) {
    exhibitionDao.update(exhibition);
  }

  @DeleteMapping
  public void delete(@RequestParam Long id) {
    exhibitionDao.delete(id);
  }
}
