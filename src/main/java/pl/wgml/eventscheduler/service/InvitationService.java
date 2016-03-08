package pl.wgml.eventscheduler.service;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import pl.wgml.eventscheduler.dao.pojo.Event;
import pl.wgml.eventscheduler.dao.pojo.Invitation;
import pl.wgml.eventscheduler.dao.pojo.User;
import pl.wgml.eventscheduler.permissions.AccessPermissions;
import pl.wgml.eventscheduler.persistence.HibernateUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InvitationService {

  UserService userService = new UserService();

  public List<Invitation> getByUser(User user, Optional<User> loggedUser) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    @SuppressWarnings("unchecked")
    List<Invitation> invitations = (List<Invitation>) session.createCriteria(Invitation.class)
        .add(Restrictions.eq("user", user)).list();
    session.close();
    return invitations.stream()
        .filter(inv -> AccessPermissions.canSeeInvitation(inv, loggedUser))
        .collect(Collectors.toList());
  }

  public List<Invitation> getByEvent(Event event) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    @SuppressWarnings("unchecked")
    List<Invitation> invitations = (List<Invitation>) session.createCriteria(Invitation.class)
        .add(Restrictions.eq("event", event)).list();
    session.close();
    return invitations;
  }

  public boolean acceptInvitation(User user, Invitation invitation) {
    return setInvStatus(user, invitation, true);
  }

  public boolean ignoreInvitation(User user, Invitation invitation) {
    return setInvStatus(user, invitation, false);
  }

  private boolean setInvStatus(User user, Invitation invitation, boolean status) {
    if (!invitation.getUser().equals(user)) {
      return false;
    }
    if (DateTime.now().isAfter(new DateTime(invitation.getEvent().getStartDate()))) {
      return false;
    }
    invitation.setAccepted(status);
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.update(invitation);
    session.getTransaction().commit();
    session.close();
    return true;
  }

  public boolean inviteUserToEvent(User user, Event event) {
    if (getByEvent(event).stream().anyMatch(inv -> inv.getUser().equals(user))) {
      return false;
    }
    if (DateTime.now().isAfter(new DateTime(event.getStartDate()))) {
      return false;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    Invitation invitation = new Invitation(user, event);
    Long id = (Long) session.save(invitation);
    session.getTransaction().commit();
    session.close();
    return id >= 0;
  }

  public boolean removeUserToEvent(User user, Event event) {
    Optional<Invitation> invitation = getByEvent(event).stream().filter(inv -> inv.getUser().equals(user)).findAny();
    if (!invitation.isPresent()) {
      return false;
    }
    if (DateTime.now().isAfter(new DateTime(event.getStartDate()))) {
      return false;
    }
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.delete(invitation.get());
    session.getTransaction().commit();
    session.close();
    return true;
  }

  public List<User> getNotInvitedForEvent(Event event) {
    List<User> invited = getByEvent(event)
        .stream()
        .map(Invitation::getUser)
        .collect(Collectors.toList());

    return userService.getAllUsers()
        .stream()
        .filter(user -> !invited.contains(user))
        .filter(user -> !user.equals(event.getCreator()))
        .collect(Collectors.toList());
  }

  public Optional<Invitation> getById(long invId) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Invitation invitation = session.get(Invitation.class, invId);
    session.close();
    return Optional.ofNullable(invitation);
  }
}
