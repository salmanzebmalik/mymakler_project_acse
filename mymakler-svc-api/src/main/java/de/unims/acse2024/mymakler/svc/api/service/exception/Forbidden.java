package de.unims.acse2024.mymakler.svc.api.service.exception;

public class Forbidden extends Exception {
  private static final long serialVersionUID = 4942011881950157180L;

  public Forbidden(String msg) {
    super(msg);
  }
}
