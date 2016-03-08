package pl.wgml.eventscheduler.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.wgml.eventscheduler.dao.pojo.User;
import pl.wgml.eventscheduler.validation.UserValidation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(
    name = "LoginServlet",
    urlPatterns = {"/login"}
)
public class LoginServlet extends HttpServlet {

  private static final Logger logger = LogManager.getLogger();
  private UserService userService = new UserService();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    showForm(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");

    String username = request.getParameter("username");
    String password = request.getParameter("password");

    if (UserValidation.nullOrEmpty(username) || UserValidation.nullOrEmpty(password)) {
      failLogin(request, response, "Please provide username and password");
      return;
    }

    Optional<User> user = userService.tryLogin(username, password);
    if (user.isPresent()) {
      logger.info("Logged as " + user.get());
      HttpSession session = request.getSession();
      session.setAttribute("loggedUser", user.get());
      session.setMaxInactiveInterval(60 * 60);

      response.sendRedirect("/user?id=" + user.get().getId());
    } else {
      logger.info("Tried to login as " + username + " with password " + password);
      failLogin(request, response, "Invalid username or password");
    }
  }

  private void failLogin(HttpServletRequest request, HttpServletResponse response, String msg) throws ServletException, IOException {
    request.setAttribute("warnMessage", msg);
    showForm(request, response);

  }

  private void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String nextJsp = "/jsp/login.jsp";
    RequestDispatcher dispatcher;
    dispatcher = getServletContext().getRequestDispatcher(nextJsp);
    dispatcher.forward(request, response);
  }
}
