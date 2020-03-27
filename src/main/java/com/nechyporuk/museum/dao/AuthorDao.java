package com.nechyporuk.museum.dao;

import com.nechyporuk.museum.entity.Author;

import java.util.List;

public interface AuthorDao {
  //  public List<Author> getAll() ;
  void save(Author author);
  List<Author> getAll();
}
