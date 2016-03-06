package pl.wgml.eventscheduler.dao.pojo;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "email")
  private String email;

  @Column(name = "admin")
  private boolean admin;

  private static AtomicLong idProvider = new AtomicLong(0);

  public User() {
  }

  public User(String username, String password, String email, UserType userType) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.admin = userType.equals(UserType.ADMINISTRATOR);
//    this.id = idProvider.incrementAndGet();
  }

  public User(Long id, String username, String password, String email, UserType userType) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.admin = userType.equals(UserType.ADMINISTRATOR);
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
    return admin ? UserType.ADMINISTRATOR : UserType.REGULAR;
  }

  public void setUserType(UserType userType) {
    this.admin = UserType.ADMINISTRATOR.equals(userType);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("username", username)
        .append("email", email)
        .append("admin", admin)
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

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }
}
