package de.unims.acse2024.mymakler.svc.api.web.controller.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import de.unims.acse2024.mymakler.svc.api.data.model.Appointment;

public class AppointmentDto implements Serializable {
  private static final long serialVersionUID = -2382144199336201195L;

  private long id;

  @NotNull
  private ViewingRequestDto request;

  @Size(max = 40)
  private String offeringUserRemarks;

  public AppointmentDto() {
  }

  public AppointmentDto(Appointment appointment) {
    this.id = appointment.getId();
    this.request = new ViewingRequestDto(appointment.getRequest());
    this.offeringUserRemarks = appointment.getOfferingUserRemarks();
  }

  public Appointment toAppointment() {
    Appointment appointment = new Appointment();
    appointment.setId(id);
    appointment.setRequest(request.toViewingRequest());
    appointment.setOfferingUserRemarks(offeringUserRemarks);
    return appointment;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public ViewingRequestDto getRequest() {
    return request;
  }

  public void setRequest(ViewingRequestDto request) {
    this.request = request;
  }

  public String getOfferingUserRemarks() {
    return offeringUserRemarks;
  }

  public void setOfferingUserRemarks(String offeringUserRemarks) {
    this.offeringUserRemarks = offeringUserRemarks;
  }
}
