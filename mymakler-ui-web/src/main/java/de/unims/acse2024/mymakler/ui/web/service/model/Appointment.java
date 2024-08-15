package de.unims.acse2024.mymakler.ui.web.service.model;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Appointment implements Serializable {
  private static final long serialVersionUID = -2382144199336201195L;

  private long id;

  @NotNull
  private ViewingRequest request;

  @Size(max = 40)
  private String offeringUserRemarks;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public ViewingRequest getRequest() {
    return request;
  }

  public void setRequest(ViewingRequest request) {
    this.request = request;
  }

  public String getOfferingUserRemarks() {
    return offeringUserRemarks;
  }

  public void setOfferingUserRemarks(String offeringUserRemarks) {
    this.offeringUserRemarks = offeringUserRemarks;
  }
}
