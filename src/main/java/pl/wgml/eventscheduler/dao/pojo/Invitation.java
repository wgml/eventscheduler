package pl.wgml.eventscheduler.dao.pojo;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Table(name = "invitation")
public class Invitation {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "event_id")
  private Event event;

  @Column(name = "accepted")
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

  public Invitation() {
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
