package de.unims.acse2024.mymakler.ui.web.service.exception;

public class ConfirmationFailed extends Exception {
  private static final long serialVersionUID = -7231511519209572386L;

  public ConfirmationFailed() {
    super("Confirmation failed");
  }

  public ConfirmationFailed(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ConfirmationFailed(String message, Throwable cause) {
    super(message, cause);
  }

  public ConfirmationFailed(String message) {
    super(message);
  }

  public ConfirmationFailed(Throwable cause) {
    super(cause);
  }
}
