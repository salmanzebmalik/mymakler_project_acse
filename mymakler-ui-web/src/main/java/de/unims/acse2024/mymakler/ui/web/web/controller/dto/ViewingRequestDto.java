package de.unims.acse2024.mymakler.ui.web.web.controller.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ViewingRequestDto implements Serializable {
  private static final long serialVersionUID = -5632922587747806289L;

  private long offerId;

  @NotBlank
  @Size(max = 150)
  private String userDescription;

  public ViewingRequestDto() {}

  public ViewingRequestDto(long offerId) {
    this.offerId = offerId;
  }

  public long getOfferId() {
    return offerId;
  }

  public void setOfferId(long viewingOfferId) {
    this.offerId = viewingOfferId;
  }

  public String getUserDescription() {
    return userDescription;
  }

  public void setUserDescription(String userDescription) {
    this.userDescription = userDescription;
  }
}
