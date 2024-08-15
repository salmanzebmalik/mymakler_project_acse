package de.unims.acse2024.mymakler.ui.web.service.model;

import java.io.Serializable;
import java.util.List;

public class AppointmentList implements Serializable {
  private List<Appointment> items;

  public List<Appointment> getItems() {
    return items;
  }

  public void setItems(List<Appointment> items) {
    this.items = items;
  }
}
