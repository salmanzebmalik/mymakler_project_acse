package de.unims.acse2024.mymakler.ui.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import de.unims.acse2024.mymakler.ui.web.service.model.ViewingOffer;
import de.unims.acse2024.mymakler.ui.web.service.model.ViewingOfferList;

@Service
public class RestViewingOfferService implements ViewingOfferService {
  @Autowired
  RestClient restClient;

  @Override
  public ViewingOffer get(long id) {
    try {
      ResponseEntity<ViewingOffer> resp = restClient.get(String.format("/viewing-offers/%d", id), ViewingOffer.class);
      return resp.getBody();
    } catch (RestClientResponseException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new NoSuchElementException("Viewing offer cannot be found");
      }
      throw e;
    }
  }

  @Override
  public List<ViewingOffer> getAllInFuture() {
    ResponseEntity<ViewingOfferList> resp = restClient.get("/viewing-offers/", ViewingOfferList.class);
    ViewingOfferList body = resp.getBody();
    if (body == null) {
      return Collections.emptyList();
    }
    return body.getItems();
  }

  @Override
  public List<ViewingOffer> getAllUntreatedInFuture(long userId) {
    try {
      ResponseEntity<ViewingOfferList> resp = restClient.get(
          String.format("/viewing-offers/?untreatedInFuture=true&userId=%d", userId),
          ViewingOfferList.class
      );
      ViewingOfferList body = resp.getBody();
      if (body == null) {
        return Collections.emptyList();
      }
      return body.getItems();
    } catch (RestClientResponseException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new NoSuchElementException("User cannot be found");
      }
      throw e;
    }
  }

  @Override
  public ViewingOffer create(ViewingOffer viewingOffer) {
    try {
      ResponseEntity<ViewingOffer> resp = restClient.post("/viewing-offers/", viewingOffer, ViewingOffer.class);
      return resp.getBody();
    } catch (RestClientResponseException e) {
      if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
        throw new IllegalArgumentException();
      }
      throw e;
    }
  }
}
