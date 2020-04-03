package com.nechyporuk.museum.dao;

import com.nechyporuk.museum.entity.Author;

import java.util.List;
import java.util.Optional;

public interface EntityDao<E> {
  void save(E entity);
  List<E> getAll();
  void delete(Long id);
  void update(E entity);
  Optional<E> getOneById(Long id);
}
