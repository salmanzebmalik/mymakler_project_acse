package de.unims.acse2024.mymakler.svc.api.data.model;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "requesting_user_id", "requested_offer_id" }))
@Entity
public class ViewingRequest implements Serializable {
  private static final long serialVersionUID = 841816892216204087L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ManyToOne
  @NotNull
  private MaklerUser requestingUser;

  @ManyToOne
  @NotNull
  private ViewingOffer requestedOffer;

  @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "request")
  private Appointment appointment;

  @NotBlank
  @Size(max = 150)
  private String userDescription;

  @NotNull
  private boolean wasDeclined;

  public MaklerUser getRequestingUser() {
    return requestingUser;
  }

  public void setRequestingUser(MaklerUser user) {
    this.requestingUser = user;
    this.requestingUser.addAddedRequest(this);
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public ViewingOffer getRequestedOffer() {
    return requestedOffer;
  }

  public void setRequestedOffer(ViewingOffer requestedOffer) {
    this.requestedOffer = requestedOffer;
    this.requestedOffer.addRequestForOffer(this);
  }

  public Appointment getAppointment() {
    return appointment;
  }

  public void setAppointment(Appointment appointment) {
    this.appointment = appointment;
  }

  public boolean isWasDeclined() {
    return wasDeclined;
  }

  public void setWasDeclined(boolean wasDeclined) {
    this.wasDeclined = wasDeclined;
  }

  public String getUserDescription() {
    return userDescription;
  }

  public void setUserDescription(String userDescription) {
    this.userDescription = userDescription;
  }
}
