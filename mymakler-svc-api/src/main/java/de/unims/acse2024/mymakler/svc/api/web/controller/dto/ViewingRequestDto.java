package de.unims.acse2024.mymakler.svc.api.web.controller.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import de.unims.acse2024.mymakler.svc.api.data.model.ViewingRequest;

public class ViewingRequestDto implements Serializable {
  private static final long serialVersionUID = 4268100578058964748L;

  private long id;

  @NotNull
  private UserDto requestingUser;

  @NotNull
  private ViewingOfferDto requestedOffer;

  @NotBlank
  @Size(max = 150)
  private String userDescription;

  @NotNull
  private boolean wasDeclined;

  public ViewingRequestDto() {
  }

  public ViewingRequestDto(ViewingRequest viewingRequest) {
    this.id = viewingRequest.getId();
    this.requestingUser = new UserDto(viewingRequest.getRequestingUser());
    this.requestedOffer = new ViewingOfferDto(viewingRequest.getRequestedOffer());
    this.userDescription = viewingRequest.getUserDescription();
    this.wasDeclined = viewingRequest.isWasDeclined();
  }

  public ViewingRequest toViewingRequest() {
    ViewingRequest viewingRequest = new ViewingRequest();
    viewingRequest.setId(id);
    viewingRequest.setRequestingUser(requestingUser.toUser());
    viewingRequest.setRequestedOffer(requestedOffer.toViewingOffer());
    viewingRequest.setUserDescription(userDescription);
    viewingRequest.setWasDeclined(wasDeclined);
    return viewingRequest;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public UserDto getRequestingUser() {
    return requestingUser;
  }

  public void setRequestingUser(UserDto requestingUser) {
    this.requestingUser = requestingUser;
  }

  public ViewingOfferDto getRequestedOffer() {
    return requestedOffer;
  }

  public void setRequestedOffer(ViewingOfferDto requestedOffer) {
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
