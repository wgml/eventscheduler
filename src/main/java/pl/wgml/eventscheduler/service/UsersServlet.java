package pl.wgml.eventscheduler.service;

import org.apache.commons.collections4.ListUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.wgml.eventscheduler.dao.pojo.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(
    name = "UsersServlet",
    urlPatterns = {"/users"}
)
public class UsersServlet extends HttpServlet {

  private static final Logger logger = LogManager.getLogger();
  private UserService service = new UserService();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    logger.debug("Received get request.");
    String action = request.getParameter("searchBy");

    List<User> result = Collections.emptyList();
    if (action == null) {
      result = service.getAllUsers();
    } else if (action.equals("id")) {
      String id = request.getParameter("id");
      try {
        Optional<User> user = service.getById(Long.valueOf(id));
        if (user.isPresent()) {
          result = Collections.singletonList(user.get());
        }
      } catch (NumberFormatException e) {
        logger.warn("Exception caught during execution.", e);
      }
    } else if (action.equals("name")) {
      String name = request.getParameter("name");
      result = ListUtils.union(service.getByName(name), service.getByEmail(name))
          .stream()
          .distinct()
          .collect(Collectors.toList());
    }
    showUsers(request, response, result);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    logger.debug("Received post request.");
    String action = request.getParameter("action");
    if (action.equals("delete")) {
      Integer id = Integer.valueOf(request.getParameter("userId"));
      boolean deleted = service.deleteUser(id);
      String msg = deleted ? "Successfully deleted user." : "Could not delete user.";
      logger.info(msg + " [id=" + id + "]");
      request.setAttribute("message", msg);
    }
    showUsers(request, response, service.getAllUsers());
  }

  private void showUsers(HttpServletRequest request, HttpServletResponse response, List<User> result) throws ServletException, IOException {
    String nextJsp = "/jsp/list-users.jsp";
    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJsp);
    request.setAttribute("userList", result);
    dispatcher.forward(request, response);
  }
}
