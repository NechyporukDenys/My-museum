package com.nechyporuk.museum.dao.impl;

import com.nechyporuk.museum.config.HibernateUtil;
import com.nechyporuk.museum.dao.AuthorDao;
import com.nechyporuk.museum.entity.Author;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class AuthorDaoImpl implements AuthorDao {

  @Override
  public void save(Author author) {
//    Transaction transaction = null;
//    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//      // start a transaction
//      transaction = session.beginTransaction();
//      // save the student object
//      session.save(author);
//      // commit transaction
//      transaction.commit();
//    } catch (Exception e) {
//      if (transaction != null) {
//        transaction.rollback();
//      }
//      e.printStackTrace();
//    }

    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.save(author);
    session.getTransaction().commit();
    session.close();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Author> getAll() {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      List allAuthors = session.createQuery("from Author").getResultList();
      return allAuthors;
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
}
}
