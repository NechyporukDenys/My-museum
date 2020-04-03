package com.nechyporuk.museum.dao.impl;

import com.nechyporuk.museum.config.HibernateUtil;
import com.nechyporuk.museum.constant.ErrorMessage;
import com.nechyporuk.museum.dao.EmployeeDao;
import com.nechyporuk.museum.entity.Employee;
import com.nechyporuk.museum.exception.NotDeletedException;
import com.nechyporuk.museum.exception.NotFoundException;
import com.nechyporuk.museum.exception.NotUpdatedException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
  @Override
  public void save(Employee entity) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      session.save(entity);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public List<Employee> getAll() {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      List allEmployees = session.createQuery("from Employee").getResultList();
      return allEmployees;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void delete(Long id) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Employee employee = session.get(Employee.class, id);
      if (employee == null) {
        throw new NotDeletedException(String.format(ErrorMessage.EMPLOYEE_NOT_DELETED_BY_ID, id));
      }
      session.delete(employee);
      transaction.commit();
    } catch (NotDeletedException e) {
      e.printStackTrace();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public void update(Employee entity) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Employee employee = session.get(Employee.class, entity.getId());
      if (employee == null) {
        throw new NotUpdatedException(String.format(ErrorMessage.EMPLOYEE_NOT_UPDATED_BY_ID, entity.getId()));
      }
      session.merge(entity);
      transaction.commit();
    } catch (NotUpdatedException e) {
      e.printStackTrace();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public Optional<Employee> getOneById(Long id) {
    Transaction transaction = null;
    Employee employee = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      employee = session.get(Employee.class, id);
      if (employee == null) {
        throw new NotFoundException(String.format(ErrorMessage.EMPLOYEE_NOT_FOUND_BY_ID, id));
      }
      transaction.commit();
    } catch (NotFoundException e) {
      e.printStackTrace();
      return Optional.empty();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
    return Optional.of(employee);
  }
}
