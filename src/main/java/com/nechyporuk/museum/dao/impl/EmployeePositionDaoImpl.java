package com.nechyporuk.museum.dao.impl;

import com.nechyporuk.museum.config.HibernateUtil;
import com.nechyporuk.museum.constant.ErrorMessage;
import com.nechyporuk.museum.dao.EmployeePositionDao;
import com.nechyporuk.museum.entity.EmployeePosition;
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
public class EmployeePositionDaoImpl implements EmployeePositionDao {
  private Logger logger = LogManager.getLogger(EmployeePositionDaoImpl.class);

  @Override
  public void save(EmployeePosition entity) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.save(entity);
      transaction.commit();
      logger.info("Employee position saved");
    } catch (Exception e) {
      logger.error("Failed to save employee position: " + e);
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public List<EmployeePosition> getAll() {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      return session.createQuery("from EmployeePosition").getResultList();
    } catch (Exception e) {
      logger.error("Failed to get all employee positions: " + e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public void delete(Long id) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      EmployeePosition employeePosition = session.get(EmployeePosition.class, id);
      if (employeePosition == null) {
        throw new NotDeletedException(String.format(ErrorMessage.EMPLOYEE_POSITION_NOT_DELETED_BY_ID, id));
      }
      session.delete(employeePosition);
      transaction.commit();
      logger.info(String.format("Employee position with id %d successfully removed", id));
    } catch (NotDeletedException e) {
      logger.error("Failed to remove employee position: " + e);
      e.printStackTrace();
    } catch (Exception e) {
      logger.error("Failed to remove employee position: " + e);
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public void update(EmployeePosition entity) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      EmployeePosition employeePosition = session.get(EmployeePosition.class, entity.getId());
      if (employeePosition == null) {
        throw new NotUpdatedException(String.format(ErrorMessage.EMPLOYEE_POSITION_NOT_UPDATED_BY_ID, entity.getId()));
      }
      session.merge(entity);
      transaction.commit();
      logger.info(String.format("Employee position with id %d successfully updated", entity.getId()));
    } catch (NotUpdatedException e) {
      logger.error("Failed to update employee position: " + e);
      e.printStackTrace();
    } catch (Exception e) {
      logger.error("Failed to update employee position: " + e);
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public Optional<EmployeePosition> getOneById(Long id) {
    Transaction transaction = null;
    EmployeePosition employeePosition = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      employeePosition = session.get(EmployeePosition.class, id);
      if (employeePosition == null) {
        throw new NotFoundException(String.format(ErrorMessage.EMPLOYEE_POSITION_NOT_FOUND_BY_ID, id));
      }
      transaction.commit();
    } catch (NotFoundException e) {
      logger.error("Failed to get employee position by id: " + e);
      e.printStackTrace();
      return Optional.empty();
    } catch (Exception e) {
      logger.error("Failed to get employee position by id: " + e);
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
    return Optional.of(employeePosition);
  }

}
