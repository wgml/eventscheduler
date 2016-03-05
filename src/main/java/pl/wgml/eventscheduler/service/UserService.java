package pl.wgml.eventscheduler.service;

import pl.wgml.eventscheduler.dao.pojo.User;
import pl.wgml.eventscheduler.dao.pojo.UserType;
import pl.wgml.eventscheduler.dao.pojo.helper.UserList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {

  private List<User> allUsers = UserList.getUsers();

  public List<User> getAllUsers() {
    return allUsers;
  }

  public List<User> getByName(String name) {
    return allUsers.stream()
        .filter(user -> user.getUsername().equalsIgnoreCase(name))
        .collect(Collectors.toList());
  }

  public List<User> getByEmail(String name) {
    return allUsers.stream()
        .filter(user -> user.getEmail().toLowerCase().contains(name.toLowerCase()))
        .collect(Collectors.toList());
  }

  public Optional<User> getById(long id) {
    return allUsers.stream()
        .filter(user -> user.getId() == id)
        .findFirst();
  }

  public boolean deleteUser(long id) {
    return allUsers.removeIf(user -> user.getId() == id && !user.getUserType().equals(UserType.ADMINISTRATOR));
  }

  public Optional<User> tryLogin(String username, String password) {
    return allUsers.stream()
        .filter(user -> user.getUsername().equals(username))
        .filter(user1 -> user1.getPassword().equals(password))
        .findAny();
  }

  public Optional<User> tryRegister(String username, String password, String email) {

    User user = new User(username, password, email, UserType.REGULAR);
    allUsers.add(user);
    return Optional.of(user);
  }
}
