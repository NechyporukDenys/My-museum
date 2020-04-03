package com.nechyporuk.museum.controller;

import com.nechyporuk.museum.dao.AuthorDao;
import com.nechyporuk.museum.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/author")
public class AuthorController {
  @Autowired
  private AuthorDao authorDao;

  @GetMapping("/one")
  public Optional<Author> getOneById(@RequestParam Long id) {
    return authorDao.getOneById(id);
  }

  @PutMapping
  public void update(@RequestBody Author author) {
    authorDao.update(author);
  }
  @PostMapping
  public void save(@RequestBody Author author) {
    authorDao.save(author);
  }

  @GetMapping
  public List<Author> getAll() {
    return authorDao.getAll();
  }

  @DeleteMapping
  public void delete(@RequestParam Long id) {
    authorDao.delete(id);
  }
}
