package com.nechyporuk.museum.dao.impl;

import com.nechyporuk.museum.config.HibernateUtil;
import com.nechyporuk.museum.constant.ErrorMessage;
import com.nechyporuk.museum.dao.ExhibitionDao;
import com.nechyporuk.museum.entity.Exhibition;
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
public class ExhibitionDaoImpl implements ExhibitionDao {
  private static Logger logger = LogManager.getLogger(ExcursionDaoImpl.class);

  @Override
  public void save(Exhibition entity) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.save(entity);
      transaction.commit();
      logger.info("Exhibition saved");
    } catch (Exception e) {
      logger.error("Failed to save exhibition: " + e);
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public List<Exhibition> getAll() {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      return session.createQuery("from Exhibition").getResultList();
    } catch (Exception e) {
      logger.error("Failed to get all exhibitions: " + e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public void delete(Long id) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Exhibition exhibition = session.get(Exhibition.class, id);
      if (exhibition == null) {
        throw new NotDeletedException(String.format(ErrorMessage.EXHIBITION_NOT_DELETED_BY_ID, id));
      }
      session.delete(exhibition);
      transaction.commit();
      logger.info(String.format("Exhibition with id %d successfully removed", id));
    } catch (NotDeletedException e) {
      logger.error("Failed to remove exhibition: " + e);
      e.printStackTrace();
    } catch (Exception e) {
      logger.error("Failed to remove exhibition: " + e);
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public void update(Exhibition entity) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Exhibition exhibition = session.get(Exhibition.class, entity.getId());
      if (exhibition == null) {
        throw new NotUpdatedException(String.format(ErrorMessage.EXHIBITION_NOT_UPDATED_BY_ID, entity.getId()));
      }
      session.merge(entity);
      transaction.commit();
      logger.info(String.format("Exhibition with id %d successfully updated", entity.getId()));
    } catch (NotUpdatedException e) {
      logger.error("Failed to update exhibition: " + e);
      e.printStackTrace();
    } catch (Exception e) {
      logger.error("Failed to update exhibition: " + e);
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public Optional<Exhibition> getOneById(Long id) {
    Transaction transaction = null;
    Exhibition exhibition = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      exhibition = session.get(Exhibition.class, id);
      if (exhibition == null) {
        throw new NotFoundException(String.format(ErrorMessage.EXHIBITION_NOT_FOUND_BY_ID, id));
      }
      transaction.commit();
    } catch (NotFoundException e) {
      logger.error("Failed to get exhibition by id: " + e);
      e.printStackTrace();
      return Optional.empty();
    } catch (Exception e) {
      logger.error("Failed to get exhibition by id: " + e);
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
    return Optional.of(exhibition);
  }
}
