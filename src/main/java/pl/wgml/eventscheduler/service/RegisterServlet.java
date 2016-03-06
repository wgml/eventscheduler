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
import java.io.IOException;
import java.util.Optional;

@WebServlet(
    name = "RegisterServlet",
    urlPatterns = {"/register"}
)
public class RegisterServlet extends HttpServlet {

  private static final Logger logger = LogManager.getLogger();
  private static final UserService userService = new UserService();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    showForm(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String email = request.getParameter("password");

    if (!UserValidation.validateUsername(username)) {
      failRegister(request, response, "Invalid username");
      return;
    }

    if (!UserValidation.validatePassword(password)) {
      failRegister(request, response, "Invalid password");
      return;
    }

    if (!UserValidation.validateEmail(email)) {
      failRegister(request, response, "Invalid email");
      return;
    }

    Optional<User> user = Optional.empty();

    try {
      user = userService.tryRegister(username, password, email);
    } catch (Exception e) {
      logger.warn("Cannot register user.", e);
    }
    if (user.isPresent()) {
      logger.info("Created new user: " + user.get());
      request.setAttribute("successMsg", "Account created. You can now login.");
      showForm(request, response);
    } else {
      logger.info(String.format("could not register user with username='%s', password='%s', email='%s'",
          username, password, email));
      request.setAttribute("wanMsg", "Registration failed, try again.");
      showForm(request, response);
    }
  }

  private void failRegister(HttpServletRequest request, HttpServletResponse response, String str) throws ServletException, IOException {
    request.setAttribute("warnMessage", str);
    logger.info("Error during registration. ", str);
    showForm(request, response);
  }

  private void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String nextJsp = "/jsp/register.jsp";
    RequestDispatcher dispatcher;
    dispatcher = getServletContext().getRequestDispatcher(nextJsp);
    dispatcher.forward(request, response);
  }

}
