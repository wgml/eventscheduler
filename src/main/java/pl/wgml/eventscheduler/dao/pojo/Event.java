package pl.wgml.eventscheduler.dao.pojo;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Table(name = "event")
public class Event {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "event_name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "cretor_id")
  private User creator;

  @Column(name = "event_start_date")
  private Date startDate;

  @Column(name = "event_end_date")
  private Date endDate;

  @Column(name = "public")
  private boolean isPublic;

  private static final AtomicLong idProvider = new AtomicLong(0);

  public Event(String name, User creator, Date startDate, Date endDate, boolean isPublic) {
    this.name = name;
    this.creator = creator;
    this.startDate = startDate;
    this.endDate = endDate;
    this.isPublic = isPublic;
//    this.id = idProvider.incrementAndGet();
  }

  public Event() {
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

  public boolean getIsPublic() {
    return isPublic;
  }

  public void setIsPublic(boolean is_public) {
    this.isPublic = is_public;
  }

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

}
