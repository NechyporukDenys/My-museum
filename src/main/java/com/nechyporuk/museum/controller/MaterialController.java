package com.nechyporuk.museum.controller;

import com.nechyporuk.museum.dao.MaterialDao;
import com.nechyporuk.museum.entity.Material;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/material")
public class MaterialController {
  private final MaterialDao materialDao;

  public MaterialController(MaterialDao materialDao) {
    this.materialDao = materialDao;
  }

  @PostMapping
  public void save(@RequestBody Material material) {
    materialDao.save(material);
  }

  @GetMapping
  public List<Material> getAll() {
    return materialDao.getAll();
  }

  @GetMapping("/one")
  public Optional<Material> getOneById(@RequestParam Long id) {
    return materialDao.getOneById(id);
  }

  @DeleteMapping
  public void delete(@RequestParam Long id) {
    materialDao.delete(id);
  }

  @PutMapping
  public void update(@RequestBody Material material) {
    materialDao.update(material);
  }
}
