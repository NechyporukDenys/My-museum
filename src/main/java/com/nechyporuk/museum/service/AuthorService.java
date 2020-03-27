package com.nechyporuk.museum.service;

import com.nechyporuk.museum.dao.AuthorDao;
import com.nechyporuk.museum.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
  @Autowired
  private AuthorDao authorDao;

  public void save(Author author) {
    authorDao.save(author);
  }

  public List<Author> getAll() {
    return authorDao.getAll();
  }
}
