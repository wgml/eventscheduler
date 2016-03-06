package pl.wgml.eventscheduler.validation;

import org.joda.time.DateTime;

public class EventValidation extends GeneralValidation {


  public static boolean validateName(String name) {
    return !nullOrEmpty(name);
  }

  public static boolean validateStartDate(DateTime startDate) {
    return startDate.isAfter(DateTime.now());
  }

  public static boolean validateEndDate(DateTime endDate) {
    return endDate.isAfter(DateTime.now());
  }
}
