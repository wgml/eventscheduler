package pl.wgml.eventscheduler.dao.pojo;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.concurrent.atomic.AtomicLong;

public class User {
  private Long id;
  private String username;
  private String password;
  private String email;
  private UserType userType;

  private static AtomicLong idProvider = new AtomicLong(0);

  public User(String username, String password, String email, UserType userType) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.userType = userType;
    this.id = idProvider.incrementAndGet();
  }

  public User(Long id, String username, String password, String email, UserType userType) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.userType = userType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserType getUserType() {
    return userType;
  }

  public void setUserType(UserType userType) {
    this.userType = userType;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("username", username)
        .append("email", email)
        .append("userType", userType)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equal(id, user.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
