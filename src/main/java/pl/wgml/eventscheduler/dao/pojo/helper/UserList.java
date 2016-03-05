package pl.wgml.eventscheduler.dao.pojo.helper;

import com.google.common.collect.Lists;
import pl.wgml.eventscheduler.dao.pojo.User;
import pl.wgml.eventscheduler.dao.pojo.UserType;

import java.util.List;

public class UserList {

  private static final List<User> list = Lists.newArrayList(
      new User("Adam Giża", "taylor1989", "adamgiza@gmail.com", UserType.ADMINISTRATOR),
      new User("Gruba Świnia", "ruchampsajaksra", "grubaswinia@gmail.com", UserType.REGULAR),
      new User("antic", "123456", "antic@agh.edu.pl", UserType.REGULAR)
  );

  public static List<User> getUsers() {
    return list;
  }
}
