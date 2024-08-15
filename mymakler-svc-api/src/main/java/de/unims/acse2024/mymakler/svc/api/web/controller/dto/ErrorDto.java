package de.unims.acse2024.mymakler.svc.api.web.controller.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;

public class ErrorDto implements Serializable {
  private static final long serialVersionUID = 5451819677127709897L;

  @NotNull
  private String errorMessage;

  public ErrorDto() {}

  public ErrorDto(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
