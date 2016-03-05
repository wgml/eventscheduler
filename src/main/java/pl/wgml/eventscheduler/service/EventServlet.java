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
    name = "EventServlet",
    urlPatterns = {"/event"}
)
public class EventServlet extends HttpServlet {
  private static final Logger logger = LogManager.getLogger();

  private UserService userService = new UserService();
  private EventService eventService = new EventService();
  private InvitationService invitationService = new InvitationService();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    logger.debug("Received get request.");
    try {
      long eventId = Long.valueOf(request.getParameter("id"));
      Event event = eventService.getById(eventId).get();
      showEvent(event, request, response);
    } catch (Exception e) {
      logger.warn("Failed to present event.", e);
      response.sendRedirect("/events");
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {    try {
    logger.debug("Received post request.");
    long eventId = Long.valueOf(request.getParameter("id"));
    Event event = eventService.getById(eventId).get();
    String action = request.getParameter("action");
    boolean status;
    User user;
    if (action.equals("invite")) {
      long userId = Long.valueOf(request.getParameter("userId"));
      user = userService.getById(userId).get();
      status = invitationService.inviteUserToEvent(user, event);
    } else {
      long userId = Long.valueOf(request.getParameter("deletedUserId"));
      user = userService.getById(userId).get();
      status = invitationService.removeUserToEvent(user, event);
    }
    String msg = status ? "Successfully modified invitation."
        : "Failed to modify invitation.";
    logger.info(msg + user + event);
    request.setAttribute("message", msg);
    showEvent(event, request, response);
  } catch (Exception e) {
    logger.warn("Failed to present event.", e);
    response.sendRedirect("/events");
  }

  }

  private void showEvent(Event event, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Invitation> invitations = invitationService.getByEvent(event);
    List<User> notInvited = invitationService.getNotInvitedForEvent(event);
    String nextJsp = "/jsp/event.jsp";
    RequestDispatcher dispatcher;
    dispatcher = getServletContext().getRequestDispatcher(nextJsp);
    request.setAttribute("event", event);
    request.setAttribute("invitations", invitations);
    request.setAttribute("notInvitedUsers", notInvited);
    dispatcher.forward(request, response);
  }
}
