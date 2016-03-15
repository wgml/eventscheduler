package pl.wgml.eventscheduler.validation;

import org.apache.commons.validator.routines.EmailValidator;

public class UserValidation extends GeneralValidation{

  public static boolean validateUsername(String username) {
    return !nullOrEmpty(username);
  }

  public static boolean validatePassword(String password) {
    if (nullOrEmpty(password)) {
      return false;
    }
    return password.length() >= 6;
  }

  public static boolean validateEmail(String email) {
    if (nullOrEmpty(email)) {
      return false;
    }
    boolean v = EmailValidator.getInstance().isValid(email);
    return v;
  }
}
