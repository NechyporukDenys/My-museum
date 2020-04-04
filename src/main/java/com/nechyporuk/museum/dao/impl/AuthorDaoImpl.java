package com.nechyporuk.museum.dao.impl;

import com.nechyporuk.museum.config.HibernateUtil;
import com.nechyporuk.museum.constant.ErrorMessage;
import com.nechyporuk.museum.dao.AuthorDao;
import com.nechyporuk.museum.entity.Author;
import com.nechyporuk.museum.exception.NotDeletedException;
import com.nechyporuk.museum.exception.NotFoundException;
import com.nechyporuk.museum.exception.NotUpdatedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorDaoImpl implements AuthorDao {
  private static Logger logger = LogManager.getLogger(AuthorDaoImpl.class);
  @Override
  public void save(Author author) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.save(author);
      transaction.commit();
      logger.info("Author saved");
    } catch (Exception e) {
      logger.error("Failed to save author: " + e);
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public List<Author> getAll() {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      List allAuthors = session.createQuery("from Author").getResultList();
      return allAuthors;
    } catch (Exception e) {
      logger.error("Failed to get all authors: " + e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public void delete(Long id) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Author author = session.get(Author.class, id);
      if (author == null) {
        throw new NotDeletedException(String.format(ErrorMessage.AUTHOR_NOT_DELETED_BY_ID, id));
      }
      session.delete(author);
      transaction.commit();
      logger.info(String.format("Author with id %d successfully removed", id));
    } catch (NotDeletedException e) {
      logger.error("Failed to remove author: " + e);
      e.printStackTrace();
    } catch (Exception e) {
      logger.error("Failed to remove author: " + e);
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public void update(Author entity) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Author author = session.get(Author.class, entity.getId());
      if (author == null) {
        throw new NotUpdatedException(String.format(ErrorMessage.AUTHOR_NOT_UPDATED_BY_ID, entity.getId()));
      }
      session.merge(entity);
      transaction.commit();
      logger.info(String.format("Author with id %d successfully updated", entity.getId()));
    } catch (NotUpdatedException e) {
      logger.error("Failed to update author: " + e);
      e.printStackTrace();
    } catch (Exception e) {
      logger.error("Failed to update author: " + e);
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public Optional<Author> getOneById(Long id) {
    Transaction transaction = null;
    Author author = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      author = session.get(Author.class, id);
      if (author == null) {
        throw new NotFoundException(String.format(ErrorMessage.AUTHOR_NOT_FOUND_BY_ID, id));
      }
      transaction.commit();
    } catch (NotFoundException e) {
      logger.error("Failed to get author by id: " + e);
      e.printStackTrace();
      return Optional.empty();
    } catch (Exception e) {
      logger.error("Failed to get author by id: " + e);
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
    return Optional.of(author);
  }
}
