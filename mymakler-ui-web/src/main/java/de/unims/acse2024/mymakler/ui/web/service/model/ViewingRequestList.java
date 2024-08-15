package de.unims.acse2024.mymakler.ui.web.service.model;

import java.io.Serializable;
import java.util.List;

public class ViewingRequestList implements Serializable {
  private List<ViewingRequest> items;

  public List<ViewingRequest> getItems() {
    return items;
  }

  public void setItems(List<ViewingRequest> items) {
    this.items = items;
  }
}
