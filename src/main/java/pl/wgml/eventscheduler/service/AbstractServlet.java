package pl.wgml.eventscheduler.service;

import pl.wgml.eventscheduler.dao.pojo.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AbstractServlet extends HttpServlet {

  protected Optional<User> getUser(HttpServletRequest request) {
    return Optional.ofNullable((User) request.getSession().getAttribute("loggedUser"));
  }

}
