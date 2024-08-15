package de.unims.acse2024.mymakler.ui.web.service.model;

import java.io.Serializable;
import java.util.List;

public class ViewingOfferList implements Serializable {
  private List<ViewingOffer> items;

  public List<ViewingOffer> getItems() {
    return items;
  }

  public void setItems(List<ViewingOffer> items) {
    this.items = items;
  }
}
