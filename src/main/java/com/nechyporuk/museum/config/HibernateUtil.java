package com.nechyporuk.museum.config;

import com.nechyporuk.museum.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
  private static SessionFactory sessionFactory;
  private static Logger logger = LogManager.getLogger(HibernateUtil.class);

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      try {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Author.class);
        configuration.addAnnotatedClass(Employee.class);
        configuration.addAnnotatedClass(EmployeePosition.class);
        configuration.addAnnotatedClass(Excursion.class);
        configuration.addAnnotatedClass(Exhibition.class);
        configuration.addAnnotatedClass(Hall.class);
        configuration.addAnnotatedClass(Material.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        logger.info("SessionFactory created");
      } catch (Exception e) {
        logger.error("SessionFactory creation failed: " + e);
      }
    }
    return sessionFactory;
  }
}