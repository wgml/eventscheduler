package pl.wgml.eventscheduler.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import pl.wgml.eventscheduler.dao.pojo.Event;

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

@WebServlet(
    name = "EventsServlet",
    urlPatterns = {"/events"}
)
public class EventsServlet extends HttpServlet {

  private static final Logger logger = LogManager.getLogger();
  private EventService eventService = new EventService();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    logger.debug("Received GET request.");
    List<Event> result = eventService.getAllEvents();
    String action = request.getParameter("searchBy");
    if (action == null) {
      result = eventService.getAllEvents();
    } else if (action.equals("id")) {
      String id = request.getParameter("id");
      try {
        Optional<Event> event = eventService.getById(Long.valueOf(id));
        if (event.isPresent()) {
          result = Collections.singletonList(event.get());
        }
      } catch (NumberFormatException e) {
        logger.warn("Exception caught during execution.", e);
      }
    } else if (action.equals("date")) {
      String date = request.getParameter("date");
      if (date != null && !date.isEmpty()) {
        result = eventService.getByDate(DateTime.parse(date));
      }
      date = request.getParameter("after");
      if (date != null && !date.isEmpty()) {
        result = eventService.getAfterDate(DateTime.parse(date));
      }
    }
    showEvents(request, response, result);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    logger.debug("Received post request.");
    String action = request.getParameter("action");
    if (action.equals("delete")) {
      Integer id = Integer.valueOf(request.getParameter("eventId"));
      boolean deleted = eventService.deleteEvent(id);
      String msg = deleted ? "Successfully deleted event." : "Could not delete event.";
      logger.info(msg + " [id=" + id + "]");
      request.setAttribute("message", msg);
    }
    showEvents(request, response, eventService.getAllEvents());
  }

  private void showEvents(HttpServletRequest request, HttpServletResponse response, List<Event> result) throws ServletException, IOException {
    String nextJsp = "/jsp/list-events.jsp";
    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJsp);
    request.setAttribute("eventList", result);
    dispatcher.forward(request, response);
  }
}
