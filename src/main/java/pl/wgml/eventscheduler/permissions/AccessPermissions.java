package pl.wgml.eventscheduler.permissions;

import pl.wgml.eventscheduler.dao.pojo.Event;
import pl.wgml.eventscheduler.dao.pojo.Invitation;
import pl.wgml.eventscheduler.dao.pojo.User;
import pl.wgml.eventscheduler.dao.pojo.UserType;
import pl.wgml.eventscheduler.service.InvitationService;

import java.util.Optional;

public class AccessPermissions {

  private static final InvitationService invitationService = new InvitationService();

  public static boolean canEditEvent(Event event, User user) {
    return event.getCreator().equals(user) || user.getUserType().equals(UserType.ADMINISTRATOR);
  }

  public static boolean canViewEvent(Event event, Optional<User> user) {
    if (user.isPresent() && user.get().getUserType().equals(UserType.ADMINISTRATOR)) {
      return true;
    }
    if (event.getIsPublic()) {
      return true;
    }
    if (!user.isPresent()) {
      return false;
    }
    if (event.getCreator().equals(user.get())) {
      return true;
    }
    return invitationService.getByEvent(event).stream()
        .anyMatch(inv -> inv.getUser().equals(user.get()));
  }

  public static boolean canDeleteUser(User user, Optional<User> loggedUser) {
    return loggedUser.isPresent() && loggedUser.get().getUserType().equals(UserType.ADMINISTRATOR) && !user.getUserType().equals(UserType.ADMINISTRATOR);
  }

  public static boolean canSeeInvitation(Invitation invitation, Optional<User> loggedUser) {
    if (loggedUser.isPresent() && invitation.getUser().equals(loggedUser.get())) {
      return true;
    }
    if (loggedUser.isPresent() && loggedUser.get().getUserType().equals(UserType.ADMINISTRATOR)) {
      return true;
    }
    return invitation.getEvent().getIsPublic();
  }

  public static boolean canEditInvitation(Invitation invitation, Optional<User> loggedUser) {
    if (!loggedUser.isPresent()) {
      return false;
    }
    return (invitation.getUser().equals(loggedUser.get()) || loggedUser.get().getUserType().equals(UserType.ADMINISTRATOR));
  }

  public static boolean canInviteToEvent(Event event, Optional<User> loggedUser) {
    if (!loggedUser.isPresent()) {
      return false;
    }
    if (event.getIsPublic()) {
      return true;
    }
    if (event.getCreator().equals(loggedUser.get())) {
      return true;
    }
    return loggedUser.get().getUserType().equals(UserType.ADMINISTRATOR);
  }

  public static boolean canRemoveFromEvent(Event event, Optional<User> loggedUser) {
    if (!loggedUser.isPresent()) {
      return false;
    }
    if (event.getCreator().equals(loggedUser.get())) {
      return true;
    }
    return loggedUser.get().getUserType().equals(UserType.ADMINISTRATOR);
  }
}
