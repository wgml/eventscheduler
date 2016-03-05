package pl.wgml.eventscheduler.dao.pojo;

public class Invitation {
  private Long id;
  private Long user_id;
  private Long event_id;
  private Long accepted;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }

  public Long getEvent_id() {
    return event_id;
  }

  public void setEvent_id(Long event_id) {
    this.event_id = event_id;
  }

  public Long getAccepted() {
    return accepted;
  }

  public void setAccepted(Long accepted) {
    this.accepted = accepted;
  }
}
