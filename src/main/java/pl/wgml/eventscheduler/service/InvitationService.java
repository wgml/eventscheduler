package pl.wgml.eventscheduler.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.wgml.eventscheduler.dao.pojo.Event;
import pl.wgml.eventscheduler.dao.pojo.Invitation;
import pl.wgml.eventscheduler.dao.pojo.User;
import pl.wgml.eventscheduler.dao.pojo.helper.InvitationList;
import pl.wgml.eventscheduler.dao.pojo.helper.UserList;
import pl.wgml.eventscheduler.permissions.AccessPermissions;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InvitationService {

  private static final Logger logger = LogManager.getLogger();

  private List<Invitation> invitations = InvitationList.getInvitations();

  public List<Invitation> getByUser(User user, Optional<User> loggedUser) {
    return invitations.stream()
        .filter(inv -> inv.getUser().getId().equals(user.getId()))
        .filter(inv -> AccessPermissions.canSeeInvitation(inv, loggedUser))
        .collect(Collectors.toList());
  }

  public List<Invitation> getByEvent(Event event) {
    return invitations.stream()
        .filter(inv -> inv.getEvent().getId().equals(event.getId()))
        .collect(Collectors.toList());
  }

  public boolean acceptInvitation(User user, long invId) {
    return setInvStatus(user, invId, true);
  }

  public boolean ignoreInvitation(User user, long invId) {
    return setInvStatus(user, invId, false);
  }

  private boolean setInvStatus(User user, long invId, boolean status) {
    Optional<Invitation> invitation = invitations.stream()
        .filter(inv -> inv.getId().equals(invId) && inv.getUser().getId().equals(user.getId()))
        .findAny();
    if (!invitation.isPresent()) {
      logger.warn("Wanted to modify invalid invitation: " + invId + ", " + user);
      return false;
    }
    logger.info("Changed invitation " + invitation.get() + " status to " + status);
    invitation.get().setAccepted(status);
    return true;
  }

  public boolean inviteUserToEvent(User user, Event event) {
    if (invitations.stream().anyMatch(inv -> inv.getUser().getId().equals(user.getId())
    && inv.getEvent().getId().equals(event.getId()))) {
      return false;
    }
    invitations.add(new Invitation(user, event));
    return true;
  }

  public boolean removeUserToEvent(User user, Event event) {
    return invitations.removeIf(inv -> inv.getUser().getId().equals(user.getId())
        && inv.getEvent().getId().equals(event.getId()));
  }

  public List<User> getNotInvitedForEvent(Event event) {
    List<User> invited = getByEvent(event)
        .stream()
        .map(Invitation::getUser)
        .collect(Collectors.toList());
    return UserList.getUsers()
        .stream()
        .filter(user -> !invited.contains(user))
        .collect(Collectors.toList());
  }

  public Optional<Invitation> getById(long invId) {
    return invitations.stream()
        .filter(inv -> inv.getId().equals(invId))
        .findFirst();
  }
}
