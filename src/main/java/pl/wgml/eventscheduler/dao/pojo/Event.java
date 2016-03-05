package pl.wgml.eventscheduler.dao.pojo;

public class Event {
  private Long id;
  private String event_name;
  private Long cretor_id;
  private java.sql.Timestamp event_start_date;
  private java.sql.Timestamp event_end_date;
  private boolean is_public;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEvent_name() {
    return event_name;
  }

  public void setEvent_name(String event_name) {
    this.event_name = event_name;
  }

  public Long getCretor_id() {
    return cretor_id;
  }

  public void setCretor_id(Long cretor_id) {
    this.cretor_id = cretor_id;
  }

  public java.sql.Timestamp getEvent_start_date() {
    return event_start_date;
  }

  public void setEvent_start_date(java.sql.Timestamp event_start_date) {
    this.event_start_date = event_start_date;
  }

  public java.sql.Timestamp getEvent_end_date() {
    return event_end_date;
  }

  public void setEvent_end_date(java.sql.Timestamp event_end_date) {
    this.event_end_date = event_end_date;
  }

  public boolean getPublic() {
    return is_public;
  }

  public void setPublic(boolean is_public) {
    this.is_public = is_public;
  }
}
