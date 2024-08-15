package de.unims.acse2024.mymakler.svc.api.data.model;

import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Entity
public class ViewingOffer implements Serializable {
  private static final long serialVersionUID = 7818194320117743916L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ManyToOne
  @NotNull
  private MaklerUser offeringUser;

  @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "requestedOffer", fetch = FetchType.EAGER)
  private Set<ViewingRequest> requestsForOffer;

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

  @CreationTimestamp
  private LocalDateTime onlineSince;

  @NotNull
  @Future
  private LocalDateTime viewingDate;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public MaklerUser getOfferingUser() {
    return offeringUser;
  }

  public void setOfferingUser(MaklerUser offeringUser) {
    this.offeringUser = offeringUser;
    this.offeringUser.addAddedOffer(this);
  }

  public void addRequestForOffer(ViewingRequest requestForOffer) {
    if (requestsForOffer == null) {
      requestsForOffer = new HashSet<>();
    }
    this.requestsForOffer.add(requestForOffer);
  }

  public Set<ViewingRequest> getRequestsForOffer() {
    return requestsForOffer;
  }

  public void setRequestsForOffer(Set<ViewingRequest> requestsForOffer) {
    this.requestsForOffer = requestsForOffer;
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

  public LocalDateTime getOnlineSince() {
    return onlineSince;
  }

  public void setOnlineSince(LocalDateTime onlineSince) {
    this.onlineSince = onlineSince;
  }

  public LocalDateTime getViewingDate() {
    return viewingDate;
  }

  public void setViewingDate(LocalDateTime viewingDate) {
    this.viewingDate = viewingDate;
  }
}
