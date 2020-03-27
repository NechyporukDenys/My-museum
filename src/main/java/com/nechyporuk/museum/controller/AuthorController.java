package com.nechyporuk.museum.controller;

import com.nechyporuk.museum.entity.Author;
import com.nechyporuk.museum.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
  @Autowired
  private AuthorService authorService;

  @PostMapping
  public void save(@RequestBody Author author) {
    authorService.save(author);
  }

  @GetMapping
  public List<Author> getAll() {
    return authorService.getAll();
  }
}
