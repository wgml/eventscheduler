package pl.wgml.eventscheduler.validation;

public class GeneralValidation {

  public static boolean nullOrEmpty(String str) {
    return str == null || str.isEmpty();
  }
}
