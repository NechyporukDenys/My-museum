package com.nechyporuk.museum.dao.impl;

import com.nechyporuk.museum.config.HibernateUtil;
import com.nechyporuk.museum.constant.ErrorMessage;
import com.nechyporuk.museum.dao.ExcursionDao;
import com.nechyporuk.museum.entity.Excursion;
import com.nechyporuk.museum.exception.NotDeletedException;
import com.nechyporuk.museum.exception.NotFoundException;
import com.nechyporuk.museum.exception.NotUpdatedException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ExcursionDaoImpl implements ExcursionDao {
  @Override
  public void save(Excursion entity) {
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
  public List<Excursion> getAll() {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      return session.createQuery("from Excursion").getResultList();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void delete(Long id) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Excursion excursion = session.get(Excursion.class, id);
      if (excursion == null) {
        throw new NotDeletedException(String.format(ErrorMessage.EXCURSION_NOT_DELETED_BY_ID, id));
      }
      session.delete(excursion);
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
  public void update(Excursion entity) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      Excursion excursion = session.get(Excursion.class, entity.getId());
      if (excursion == null) {
        throw new NotUpdatedException(String.format(ErrorMessage.EXCURSION_NOT_UPDATED_BY_ID, entity.getId()));
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
  public Optional<Excursion> getOneById(Long id) {
    Transaction transaction = null;
    Excursion excursion = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      excursion = session.get(Excursion.class, id);
      if (excursion == null) {
        throw new NotFoundException(String.format(ErrorMessage.EXCURSION_NOT_FOUND_BY_ID, id));
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
    return Optional.of(excursion);
  }

}
