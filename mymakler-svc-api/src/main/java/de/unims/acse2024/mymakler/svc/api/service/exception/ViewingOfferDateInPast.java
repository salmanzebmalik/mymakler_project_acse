package de.unims.acse2024.mymakler.svc.api.service.exception;

public class ViewingOfferDateInPast extends Exception {
  private static final long serialVersionUID = 5352166545952912975L;

  public ViewingOfferDateInPast() {
    super();
  }

  public ViewingOfferDateInPast(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ViewingOfferDateInPast(String message, Throwable cause) {
    super(message, cause);
  }

  public ViewingOfferDateInPast(String message) {
    super(message);
  }

  public ViewingOfferDateInPast(Throwable cause) {
    super(cause);
  }
}
