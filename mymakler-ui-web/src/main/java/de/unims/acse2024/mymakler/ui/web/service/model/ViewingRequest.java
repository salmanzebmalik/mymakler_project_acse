package de.unims.acse2024.mymakler.ui.web.service.model;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ViewingRequest implements Serializable {
  private static final long serialVersionUID = 4268100578058964748L;

  private long id;

  @NotNull
  private User requestingUser;

  @NotNull
  private ViewingOffer requestedOffer;

  @NotBlank
  @Size(max = 150)
  private String userDescription;

  @NotNull
  private boolean wasDeclined;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public User getRequestingUser() {
    return requestingUser;
  }

  public void setRequestingUser(User requestingUser) {
    this.requestingUser = requestingUser;
  }

  public ViewingOffer getRequestedOffer() {
    return requestedOffer;
  }

  public void setRequestedOffer(ViewingOffer requestedOffer) {
    this.requestedOffer = requestedOffer;
  }

  public String getUserDescription() {
    return userDescription;
  }

  public void setUserDescription(String userDescription) {
    this.userDescription = userDescription;
  }

  public boolean isWasDeclined() {
    return wasDeclined;
  }

  public void setWasDeclined(boolean wasDeclined) {
    this.wasDeclined = wasDeclined;
  }
}
