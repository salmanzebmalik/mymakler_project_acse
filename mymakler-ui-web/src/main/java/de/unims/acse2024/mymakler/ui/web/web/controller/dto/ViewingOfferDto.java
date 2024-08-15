package de.unims.acse2024.mymakler.ui.web.web.controller.dto;

import java.io.Serializable;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import de.unims.acse2024.mymakler.ui.web.service.model.ViewingOffer;

public class ViewingOfferDto implements Serializable {
  private static final long serialVersionUID = -5632922587747806289L;

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

  private String viewingDate;

  public ViewingOfferDto() {}

  public ViewingOffer toViewingOffer() {
    ViewingOffer viewingOffer = new ViewingOffer();
    viewingOffer.setTitle(title);
    viewingOffer.setDescription(description);
    viewingOffer.setSqMeters(sqMeters);
    viewingOffer.setCity(city);
    viewingOffer.setStreet(street);
    viewingOffer.setPostalCode(postalCode);
    viewingOffer.setViewingDate(viewingDate);
    return viewingOffer;
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

  public String getViewingDate() {
    return viewingDate;
  }

  public void setViewingDate(String viewingDate) {
    this.viewingDate = viewingDate;
  }
}
