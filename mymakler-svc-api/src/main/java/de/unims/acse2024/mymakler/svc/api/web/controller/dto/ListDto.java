package de.unims.acse2024.mymakler.svc.api.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ListDto<E> implements Serializable {
  private static final long serialVersionUID = -1565734766047835295L;

  private List<E> items;

  public ListDto() {}

  public ListDto(List<E> items) {
    this.items = items;
  }

  public List<E> getItems() {
    return items;
  }

  public void setItems(List<E> items) {
    this.items = items;
  }

  @JsonProperty
  public int getTotal() {
    return items.size();
  }
}
