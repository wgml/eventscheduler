package pl.wgml.eventscheduler.dao.pojo;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class Event {
  private Long id;
  private String name;
  private User creator;
  private Date startDate;
  private Date endDate;
  private boolean isPublic;

  private static final AtomicLong idProvider = new AtomicLong(0);

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("name", name)
        .append("creator", creator)
        .append("startDate", startDate)
        .append("endDate", endDate)
        .append("isPublic", isPublic)
        .toString();
  }

  public Event(String name, User creator, Date startDate, Date endDate, boolean isPublic) {
    this.name = name;
    this.creator = creator;
    this.startDate = startDate;
    this.endDate = endDate;
    this.isPublic = isPublic;
    this.id = idProvider.incrementAndGet();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public boolean getPublic() {
    return isPublic;
  }

  public void setPublic(boolean is_public) {
    this.isPublic = is_public;
  }
}
