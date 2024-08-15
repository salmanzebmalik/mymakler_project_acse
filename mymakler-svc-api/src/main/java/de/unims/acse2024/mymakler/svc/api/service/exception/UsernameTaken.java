package de.unims.acse2024.mymakler.svc.api.service.exception;

public class UsernameTaken extends Exception {
  private static final long serialVersionUID = -9205168854573835824L;

  public UsernameTaken() {
    super();
  }

  public UsernameTaken(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public UsernameTaken(String message, Throwable cause) {
    super(message, cause);
  }

  public UsernameTaken(String message) {
    super(message);
  }

  public UsernameTaken(Throwable cause) {
    super(cause);
  }
}
