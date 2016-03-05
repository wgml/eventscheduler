package pl.wgml.eventscheduler.dao.pojo;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.concurrent.atomic.AtomicLong;

public class Invitation {
  private Long id;
  private User user;
  private Event event;
  private boolean accepted;

  private static final AtomicLong idProvider = new AtomicLong(0);

  public Invitation(User user, Event event) {
    this(user, event, false);
  }

  public Invitation(User user, Event event, boolean accepted) {
    this.user = user;
    this.event = event;
    this.accepted = accepted;
    this.id = idProvider.incrementAndGet();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

  public boolean getAccepted() {
    return accepted;
  }

  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("user", user)
        .append("event", event)
        .append("accepted", accepted)
        .toString();
  }
}
