package pl.wgml.eventscheduler.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import pl.wgml.eventscheduler.dao.pojo.Event;
import pl.wgml.eventscheduler.dao.pojo.User;
import pl.wgml.eventscheduler.permissions.AccessPermissions;
import pl.wgml.eventscheduler.validation.EventValidation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(
    name = "EditEventServlet",
    urlPatterns = {"/editevent"}
)
public class EditEventServlet extends AbstractServlet {

  private static final Logger logger = LogManager.getLogger();

  EventService eventService = new EventService();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      Optional<User> user = getUser(request);
      if (!user.isPresent()) {
        throw new Exception("Not logged in");
      }
      String eventIdStr = request.getParameter("id");
      if (eventIdStr != null && !eventIdStr.isEmpty()) {
        Optional<Event> event = eventService.getById(Long.parseLong(eventIdStr), getUser(request));
        if (!event.isPresent()) {
          throw new Exception("Invalid event id.");
        }
        if (!AccessPermissions.canEditEvent(event.get(), user.get())) {
          throw new Exception("Cannot edit given event.");
        }
        request.setAttribute("event", event.get());
      }
      showForm(request, response);
    } catch (Exception e) {
      logger.warn("Exception caught.", e);
      response.sendRedirect("/events");
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      if (request.getParameter("deleteAction") != null) {
        deleteEvent(request, response);
      } else {
        handleForm(request, response);
      }
    } catch (Exception e) {
      logger.warn("Exception during doPost.", e);
      request.setAttribute("message", e.getMessage());
      showForm(request, response);
    }
  }

  private void deleteEvent(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Integer id = Integer.valueOf(request.getParameter("deletedEventId"));
    boolean deleted = eventService.deleteEvent(id);
    String msg = deleted ? "Successfully deleted event." : "Could not delete event.";
    logger.info(msg + " [id=" + id + "]");
    request.setAttribute("message", msg);
    response.sendRedirect("/events");
  }

  private void handleForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
    User user = (User) request.getSession().getAttribute("loggedUser");
    if (user == null) {
      throw new Exception("Not logged in when modifying event.");
    }
    String action = request.getParameter("action");
    String name = request.getParameter("name");
    boolean isPublic = request.getParameter("isPublic") != null;
    DateTime startDate = DateTime.parse(request.getParameter("startDate"));
    DateTime endDate = DateTime.parse(request.getParameter("endDate"));

    String errorMsg = null;
    if (!EventValidation.validateName(name)) {
      errorMsg = "Invalid name";
    }
    if (!EventValidation.validateStartDate(startDate)) {
      errorMsg = "Invalid start date";
    }
    if (!EventValidation.validateEndDate(endDate)) {
      errorMsg = "Invalid end date";
    }
    if (endDate.isBefore(startDate)) {
      errorMsg = "End must be after start.";
    }

    if (errorMsg != null) {
      throw new Exception(errorMsg);
    }

    Optional<Event> event;
    if (action.equals("add")) {
      event = eventService.addEvent(user, name, startDate, endDate, isPublic);
    } else {
      Long eventId = Long.valueOf(request.getParameter("editEventId"));
      event = eventService.editEvent(user, eventId, name, startDate, endDate, isPublic);
    }
    if (event.isPresent()) {
      response.sendRedirect("/event?id=" + event.get().getId());
    } else {
      showForm(request, response);
    }
  }

  private void showForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String nextJsp = "/jsp/edit-event.jsp";
    RequestDispatcher dispatcher;
    dispatcher = getServletContext().getRequestDispatcher(nextJsp);
    dispatcher.forward(request, response);
  }
}
