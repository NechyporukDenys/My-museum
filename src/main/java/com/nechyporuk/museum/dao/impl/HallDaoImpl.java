package com.nechyporuk.museum.dao.impl;

import com.nechyporuk.museum.config.HibernateUtil;
import com.nechyporuk.museum.constant.ErrorMessage;
import com.nechyporuk.museum.dao.HallDao;
import com.nechyporuk.museum.entity.Hall;
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
public class HallDaoImpl implements HallDao {
  private static Logger logger = LogManager.getLogger(HallDaoImpl.class);

  @Override
  public void save(Hall entity) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.save(entity);
      transaction.commit();
      logger.info("Hall saved");
    } catch (Exception e) {
      logger.error("Failed to save hall: " + e);
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public List<Hall> getAll() {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      return session.createQuery("from Hall").getResultList();
    } catch (Exception e) {
      logger.error("Failed to get all halls: " + e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public void delete(Long id) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Hall hall = session.get(Hall.class, id);
      if (hall == null) {
        throw new NotDeletedException(String.format(ErrorMessage.HALL_NOT_DELETED_BY_ID, id));
      }
      session.delete(hall);
      transaction.commit();
      logger.info(String.format("Hall with id %d successfully removed", id));
    } catch (NotDeletedException e) {
      logger.error("Failed to remove hall: " + e);
      e.printStackTrace();
    } catch (Exception e) {
      logger.error("Failed to remove hall: " + e);
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public void update(Hall entity) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Hall hall = session.get(Hall.class, entity.getId());
      if (hall == null) {
        throw new NotUpdatedException(String.format(ErrorMessage.HALL_NOT_UPDATED_BY_ID, entity.getId()));
      }
      session.merge(entity);
      transaction.commit();
      logger.info(String.format("Hall with id %d successfully updated", entity.getId()));
    } catch (NotUpdatedException e) {
      logger.error("Failed to update hall: " + e);
      e.printStackTrace();
    } catch (Exception e) {
      logger.error("Failed to update hall: " + e);
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public Optional<Hall> getOneById(Long id) {
    Transaction transaction = null;
    Hall hall = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      hall = session.get(Hall.class, id);
      if (hall == null) {
        throw new NotFoundException(String.format(ErrorMessage.HALL_NOT_FOUND_BY_ID, id));
      }
      transaction.commit();
    } catch (NotFoundException e) {
      logger.error("Failed to get hall by id: " + e);
      e.printStackTrace();
      return Optional.empty();
    } catch (Exception e) {
      logger.error("Failed to get hall by id: " + e);
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
    return Optional.of(hall);
  }
}


