package pl.wgml.eventscheduler.validation;

public class UserValidation {

  public static boolean nullOrEmpty(String str) {
    return str == null || str.isEmpty();
  }

  public static boolean validateUsername(String username) {
    return !nullOrEmpty(username);
  }

  public static boolean validatePassword(String password) {
    return !nullOrEmpty(password);
  }

  public static boolean validateEmail(String email) {
    return !nullOrEmpty(email);
  }
}
