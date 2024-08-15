package de.unims.acse2024.mymakler.svc.api.data.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "request_id"))
public class Appointment implements Serializable {
  private static final long serialVersionUID = 7963596183081206458L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NotNull
  @OneToOne
  private ViewingRequest request;

  @Size(max = 40)
  private String offeringUserRemarks;

  public Appointment() {}

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public ViewingRequest getRequest() {
    return request;
  }

  public void setRequest(ViewingRequest viewingRequest) {
    this.request = viewingRequest;
    viewingRequest.setAppointment(this);
  }

  public String getOfferingUserRemarks() {
    return offeringUserRemarks;
  }

  public void setOfferingUserRemarks(String offeringUserRemarks) {
    this.offeringUserRemarks = offeringUserRemarks;
  }
}
