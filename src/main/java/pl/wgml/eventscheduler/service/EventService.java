package pl.wgml.eventscheduler.service;

import org.joda.time.DateTime;
import pl.wgml.eventscheduler.dao.pojo.Event;
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
}
