package de.unims.acse2024.mymakler.ui.web.service.exception;

public class PasswordNotLongEnough extends Exception {
  private static final long serialVersionUID = 3724282834396910608L;

  public PasswordNotLongEnough() {
    super("The chosen password is too short.");
  }

  public PasswordNotLongEnough(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public PasswordNotLongEnough(String message, Throwable cause) {
    super(message, cause);
  }

  public PasswordNotLongEnough(String message) {
    super(message);
  }

  public PasswordNotLongEnough(Throwable cause) {
    super(cause);
  }
}
