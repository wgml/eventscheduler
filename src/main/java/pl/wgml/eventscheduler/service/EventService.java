package pl.wgml.eventscheduler.service;

import org.joda.time.DateTime;
import pl.wgml.eventscheduler.dao.pojo.Event;
import pl.wgml.eventscheduler.dao.pojo.User;
import pl.wgml.eventscheduler.dao.pojo.UserType;
import pl.wgml.eventscheduler.dao.pojo.helper.EventList;
import pl.wgml.eventscheduler.permissions.AccessPermissions;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventService {

  private List<Event> allEvents = EventList.getEvents();

  public List<Event> getAllEvents(Optional<User> user) {
    return allEvents.stream()
        .filter(event -> AccessPermissions.canViewEvent(event, user))
        .collect(Collectors.toList());
  }

  public List<Event> getByCreator(User creator, Optional<User> user) {
    return allEvents.stream()
        .filter(event -> event.getCreator().getId().equals(creator.getId()))
        .filter(event -> AccessPermissions.canViewEvent(event, user))
        .collect(Collectors.toList());
  }

  public List<Event> getAfterDate(DateTime dateTime, Optional<User> user) {
    return allEvents.stream()
        .filter(event -> new DateTime(event.getStartDate()).isAfter(dateTime))
        .filter(event -> AccessPermissions.canViewEvent(event, user))
        .collect(Collectors.toList());
  }

  public List<Event> getByDate(DateTime dateTime, Optional<User> user) {
    return allEvents.stream()
        .filter(event -> new DateTime(event.getStartDate()).toLocalDate().equals(dateTime.toLocalDate()))
        .filter(event -> AccessPermissions.canViewEvent(event, user))
        .collect(Collectors.toList());
  }

  public Optional<Event> getById(long id, Optional<User> user) {
    return allEvents.stream()
        .filter(event -> event.getId() == id)
        .filter(event -> AccessPermissions.canViewEvent(event, user))
        .findFirst();
  }

  public boolean deleteEvent(long id) {
    return allEvents.removeIf(event -> event.getId() == id);
  }

  public Optional<Event> addEvent(User user, String name, DateTime startDate, DateTime endDate, boolean isPublic) {
    Event e = new Event(name, user, startDate.toDate(), endDate.toDate(), isPublic);
    allEvents.add(e);
    return Optional.of(e);
  }

  public Optional<Event> editEvent(User user, Long eventId, String name, DateTime startDate, DateTime endDate, boolean isPublic) throws Exception {
    Optional<Event> e = getById(eventId, Optional.ofNullable(user));
    if (!e.isPresent()) {
      throw new Exception("Invalid event id");
    }
    if (!AccessPermissions.canEditEvent(e.get(), user)) {
      throw new Exception("User " + user + " cannot edit event " + e.get());
    }
    e.get().setName(name);
    e.get().setStartDate(startDate.toDate());
    e.get().setEndDate(endDate.toDate());
    e.get().setIsPublic(isPublic);
    return e;
  }
}
