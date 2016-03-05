package pl.wgml.eventscheduler.service;

import pl.wgml.eventscheduler.dao.pojo.User;
import pl.wgml.eventscheduler.dao.pojo.UserType;
import pl.wgml.eventscheduler.dao.pojo.helper.UserList;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {

  private List<User> allusers = UserList.getUsers();

  public List<User> getAllUsers() {
    return allusers;
  }

  public List<User> getByName(String name) {
    return allusers.stream()
        .filter(user -> user.getUsername().equalsIgnoreCase(name))
        .collect(Collectors.toList());
  }

  public List<User> getByEmail(String name) {
    return allusers.stream()
        .filter(user -> user.getEmail().toLowerCase().contains(name.toLowerCase()))
        .collect(Collectors.toList());
  }

  public List<User> getById(long id) {
    return allusers.stream()
        .filter(user -> user.getId() == id)
        .collect(Collectors.toList());
  }

  public boolean deleteUser(long id) {
    return allusers.removeIf(user -> user.getId() == id && !user.getUserType().equals(UserType.ADMINISTRATOR));
  }
}
