package pl.wgml.eventscheduler.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.wgml.eventscheduler.dao.pojo.Event;
import pl.wgml.eventscheduler.dao.pojo.Invitation;
import pl.wgml.eventscheduler.dao.pojo.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
    name = "UserServlet",
    urlPatterns = {"/user"}
)
public class UserServlet extends HttpServlet {

  private static final Logger logger = LogManager.getLogger();

  private UserService userService = new UserService();
  private EventService eventService = new EventService();
  private InvitationService invitationService = new InvitationService();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    logger.debug("Received get request.");
    try {
      int userId = Integer.valueOf(request.getParameter("id"));
      User user = userService.getById(userId).get();
      showUser(request, response, user);

    } catch (Exception e) {
      logger.warn("Cannot parse user info.", e);
      response.sendRedirect("/users");
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    logger.debug("Received post request.");
    try {
      int userId = Integer.valueOf(request.getParameter("id"));
      User user = userService.getById(userId).get();
      int invId = Integer.valueOf(request.getParameter("invId"));
      String action = request.getParameter("action");
      if (action.equals("accept")) {
        invitationService.acceptInvitation(user, invId);
      } else {
        invitationService.ignoreInvitation(user, invId);
      }
      showUser(request, response, user);
    } catch (Exception e) {
      logger.warn("Cannot parse user info.", e);
      response.sendRedirect("/users");
    }
  }

  private void showUser(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
    List<Event> userCreatedEvents = eventService.getByCreator(user);
    List<Invitation> invitations = invitationService.getByUser(user);

    String nextJsp = "/jsp/user.jsp";
    RequestDispatcher dispatcher;
    dispatcher = getServletContext().getRequestDispatcher(nextJsp);
    request.setAttribute("user", user);
    request.setAttribute("events", userCreatedEvents);
    request.setAttribute("invitations", invitations);
    dispatcher.forward(request, response);
  }
}
