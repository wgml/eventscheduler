package pl.wgml.eventscheduler.dao.pojo.helper;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import pl.wgml.eventscheduler.dao.pojo.Event;
import pl.wgml.eventscheduler.dao.pojo.User;

import java.util.List;

public class EventList {

  private static final List<User> users = UserList.getUsers();
  private static final List<Event> list = Lists.newArrayList(
      new Event("Spierdolony event 1", users.get(0), DateTime.now().plusDays(1).toDate(), DateTime.now().plusHours(25).toDate(), true),
      new Event("Spierdolony event 2", users.get(1), DateTime.now().minusDays(1).toDate(), DateTime.now().minusHours(22).toDate(), true),
      new Event("Spierdolony event 3", users.get(1), DateTime.now().plusDays(2).toDate(), DateTime.now().plusDays(3).toDate(), false),
      new Event("Spierdolony event 4", users.get(2), DateTime.now().plusDays(2).toDate(), DateTime.now().plusDays(3).toDate(), false)
  );

  public static List<Event> getEvents() {
    return list;
  }
}
