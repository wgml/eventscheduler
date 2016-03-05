package pl.wgml.eventscheduler.dao.pojo.helper;

import com.google.common.collect.Lists;
import pl.wgml.eventscheduler.dao.pojo.Event;
import pl.wgml.eventscheduler.dao.pojo.Invitation;
import pl.wgml.eventscheduler.dao.pojo.User;

import java.util.List;

public class InvitationList {

  private static final List<User> users = UserList.getUsers();
  private static final List<Event> events = EventList.getEvents();
  private static final List<Invitation> list = Lists.newArrayList(
      new Invitation(users.get(0), events.get(1), true),
      new Invitation(users.get(0), events.get(2), false),
      new Invitation(users.get(0), events.get(0), true),
      new Invitation(users.get(1), events.get(1), true),
      new Invitation(users.get(1), events.get(2), false),
      new Invitation(users.get(2), events.get(1), true),
      new Invitation(users.get(2), events.get(2), false),
      new Invitation(users.get(2), events.get(0), true)
  );

  public static List<Invitation> getInvitations() {
    return list;
  }

}
