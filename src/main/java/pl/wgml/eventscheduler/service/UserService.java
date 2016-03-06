package pl.wgml.eventscheduler.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import pl.wgml.eventscheduler.dao.pojo.User;
import pl.wgml.eventscheduler.dao.pojo.UserType;
import pl.wgml.eventscheduler.permissions.AccessPermissions;
import pl.wgml.eventscheduler.persistence.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class UserService {

  public List<User> getAllUsers() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    @SuppressWarnings("unchecked")
    List<User> users = (List<User>) session.createQuery("from User").list();
    session.close();
    return users;
  }

  public List<User> getByName(String name) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    @SuppressWarnings("unchecked")
    List<User> users = (List<User>) session.createCriteria(User.class)
        .add(Restrictions.eq("username", name)).list();
    session.close();
    return users;

  }

  public List<User> getByEmail(String name) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    @SuppressWarnings("unchecked")
    List<User> users = (List<User>) session.createCriteria(User.class)
        .add(Restrictions.like("email", name, MatchMode.ANYWHERE)).list();
    session.close();
    return users;
  }

  public Optional<User> getById(long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    User user = session.get(User.class, id);
    session.close();
    return Optional.ofNullable(user);
  }

  public boolean deleteUser(long id, Optional<User> loggedUser) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    User user = session.get(User.class, id);
    if (user == null) {
      return false;
    }
    if (AccessPermissions.canDeleteUser(user, loggedUser)) {
      Transaction transaction = session.beginTransaction();
      session.delete(user);
      transaction.commit();
      session.close();
      return true;
    } else {
      return false;
    }
  }

  public Optional<User> tryLogin(String username, String password) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    @SuppressWarnings("unchecked")
    List<User> users = (List<User>) session.createCriteria(User.class)
        .add(Restrictions.and(
            Restrictions.eq("username", username),
            Restrictions.eq("password", password)
        )).list();
    session.close();
    if (users.size() == 0) {
      return Optional.empty();
    } else {
      return Optional.ofNullable(users.get(0));
    }
  }

  public Optional<User> tryRegister(String username, String password, String email) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    User user = new User(username, password, email, UserType.REGULAR);
    Long id = (Long) session.save(user);
    session.getTransaction().commit();
    session.close();
    return Optional.of(user).filter(u -> id >= 0);
  }
}
