package de.unims.acse2024.mymakler.svc.frauddetection.service.model;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class ViewingOfferDto implements Serializable {
  private static final long serialVersionUID = -5907335493502353566L;

  private long id;

  @NotNull
  private UserDto offeringUser;

  @NotBlank
  private String title;

  @NotBlank
  private String description;

  @Positive
  private int sqMeters;

  @NotBlank
  @Pattern(regexp = "[a-zA-Z\\- ]+")
  private String city;

  @NotBlank
  @Pattern(regexp = "[a-zA-Z,\\- ]+ [0-9]+")
  private String street;

  @NotBlank
  @Pattern(regexp = "[a-zA-Z0-9]+")
  private String postalCode;

  private String onlineSince;

  @NotNull
  private String viewingDate;

  public ViewingOfferDto() {}

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public UserDto getOfferingUser() {
    return offeringUser;
  }

  public void setOfferingUser(UserDto offeringUser) {
    this.offeringUser = offeringUser;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getSqMeters() {
    return sqMeters;
  }

  public void setSqMeters(int sqMeters) {
    this.sqMeters = sqMeters;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getOnlineSince() {
    return onlineSince;
  }

  public void setOnlineSince(String onlineSince) {
    this.onlineSince = onlineSince;
  }

  public String getViewingDate() {
    return viewingDate;
  }

  public void setViewingDate(String viewingDate) {
    this.viewingDate = viewingDate;
  }
}
