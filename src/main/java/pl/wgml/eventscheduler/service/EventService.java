package pl.wgml.eventscheduler.service;

import org.joda.time.DateTime;
import pl.wgml.eventscheduler.dao.pojo.Event;
import pl.wgml.eventscheduler.dao.pojo.User;
import pl.wgml.eventscheduler.dao.pojo.helper.EventList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventService {

  private List<Event> allEvents = EventList.getEvents();

  public List<Event> getAllEvents() {
    return allEvents;
  }

  public List<Event> getByName(String name) {
    return allEvents.stream()
        .filter(event -> event.getName().toLowerCase().contains(name.toLowerCase()))
        .collect(Collectors.toList());
  }

  public List<Event> getByCreator(User creator) {
    return allEvents.stream()
        .filter(event -> event.getCreator().getId().equals(creator.getId()))
        .collect(Collectors.toList());
  }

  public List<Event> getAfterDate(DateTime dateTime) {
    return allEvents.stream()
        .filter(event -> new DateTime(event.getStartDate()).isAfter(dateTime))
        .collect(Collectors.toList());
  }

  public List<Event> getByDate(DateTime dateTime) {
    return allEvents.stream()
        .filter(event -> new DateTime(event.getStartDate()).toLocalDate().equals(dateTime.toLocalDate()))
        .collect(Collectors.toList());
  }

  public Optional<Event> getById(long id) {
    return allEvents.stream()
        .filter(event -> event.getId() == id)
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

  public Optional<Event> editEvent(User user, Long eventId, String name, DateTime startDate, DateTime endDate, boolean isPublic) {
    Optional<Event> e = getById(eventId);
    if (!e.get().getCreator().equals(user)) {
      return Optional.empty();
    } else {
      e.get().setName(name);
      e.get().setStartDate(startDate.toDate());
      e.get().setEndDate(endDate.toDate());
      e.get().setIsPublic(isPublic);
      return e;
    }
  }
}
