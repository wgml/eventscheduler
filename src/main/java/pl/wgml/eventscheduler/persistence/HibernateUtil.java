package pl.wgml.eventscheduler.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

  private static final Logger logger = LogManager.getLogger();
  private static final SessionFactory sessionFactory = buildSessionFactory();

  private static SessionFactory buildSessionFactory() {
    try {
      return new Configuration()
          .configure()
          .buildSessionFactory();
    } catch (Exception e) {
      logger.error("Failed to initialize session factory.", e);
      throw new ExceptionInInitializerError(e);
    }
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }
}
