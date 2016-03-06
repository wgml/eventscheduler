package pl.wgml.eventscheduler.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import pl.wgml.eventscheduler.dao.pojo.Event;
import pl.wgml.eventscheduler.dao.pojo.User;
import pl.wgml.eventscheduler.permissions.AccessPermissions;
import pl.wgml.eventscheduler.persistence.HibernateUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventService {

  public List<Event> getAllEvents(Optional<User> user) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    @SuppressWarnings("unchecked")
    List<Event> events = (List<Event>) session.createQuery("from Event").list();
    session.close();
    return events.stream()
        .filter(event -> AccessPermissions.canViewEvent(event, user))
        .collect(Collectors.toList());
  }

  public List<Event> getByCreator(User creator, Optional<User> user) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    @SuppressWarnings("unchecked")
    List<Event> events = (List<Event>) session.createCriteria(Event.class)
        .add(Restrictions.eq("creator", creator)).list();
    session.close();
    return events.stream()
        .filter(event -> AccessPermissions.canViewEvent(event, user))
        .collect(Collectors.toList());
  }

  public List<Event> getAfterDate(DateTime dateTime, Optional<User> user) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    @SuppressWarnings("unchecked")
    List<Event> events = (List<Event>) session.createCriteria(Event.class)
        .add(Restrictions.ge("startDate", dateTime.toDate())).list();
    session.close();
    return events.stream()
        .filter(event -> AccessPermissions.canViewEvent(event, user))
        .collect(Collectors.toList());
  }

  public List<Event> getByDate(DateTime dateTime, Optional<User> user) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    @SuppressWarnings("unchecked")
    List<Event> events = (List<Event>) session.createCriteria(Event.class)
        .add(Restrictions.between("startDate", dateTime.toDate(), dateTime.plusDays(1).toDate())).list();
    session.close();
    return events.stream()
        .filter(event -> AccessPermissions.canViewEvent(event, user))
        .collect(Collectors.toList());
  }

  public Optional<Event> getById(long id, Optional<User> user) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Event event = session.get(Event.class, id);
    session.close();
    return Optional.ofNullable(event)
        .filter(e -> AccessPermissions.canViewEvent(e,user));
  }

  public boolean deleteEvent(long id) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Event event = session.get(Event.class, id);
    if (event == null) {
      return false;
    }
    Transaction transaction = session.beginTransaction();
    session.delete(event);
    transaction.commit();
    session.close();
    return true;
  }

  public Optional<Event> addEvent(User user, String name, DateTime startDate, DateTime endDate, boolean isPublic) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    Event event = new Event(name, user, startDate.toDate(), endDate.toDate(), isPublic);
    Long id = (Long) session.save(event);
    session.getTransaction().commit();
    session.close();
    return Optional.of(event).filter(e -> id >= 0);
  }

  public Optional<Event> editEvent(User user, Long eventId, String name, DateTime startDate, DateTime endDate, boolean isPublic) throws Exception {
    Optional<Event> event = getById(eventId, Optional.ofNullable(user));
    if (!event.isPresent()) {
      throw new Exception("Invalid event id");
    }
    if (!AccessPermissions.canEditEvent(event.get(), user)) {
      throw new Exception("User " + user + " cannot edit event " + event.get());
    }
    event.get().setName(name);
    event.get().setStartDate(startDate.toDate());
    event.get().setEndDate(endDate.toDate());
    event.get().setIsPublic(isPublic);
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.update(event.get());
    session.getTransaction().commit();
    session.close();
    return event;
  }
}
