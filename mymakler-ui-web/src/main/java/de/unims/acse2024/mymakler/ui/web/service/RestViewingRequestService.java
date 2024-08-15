package de.unims.acse2024.mymakler.ui.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import de.unims.acse2024.mymakler.ui.web.service.model.User;
import de.unims.acse2024.mymakler.ui.web.service.model.ViewingOffer;
import de.unims.acse2024.mymakler.ui.web.service.model.ViewingRequest;
import de.unims.acse2024.mymakler.ui.web.service.exception.Forbidden;
import de.unims.acse2024.mymakler.ui.web.service.model.ViewingRequestList;

@Service
public class RestViewingRequestService implements ViewingRequestService {
  @Autowired
  RestClient restClient;

  @Autowired
  ViewingOfferService viewingOfferService;

  @Autowired
  UserService userService;

  @Override
  public ViewingRequest get(long id) {
    try {
      ResponseEntity<ViewingRequest> resp = restClient.get(String.format("/viewing-requests/%d", id), ViewingRequest.class);
      return resp.getBody();
    } catch (RestClientResponseException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new NoSuchElementException("Viewing request cannot be found");
      }
      throw e;
    }
  }

  @Override
  public List<ViewingRequest> getAllUntreatedByLandlordInFuture(long userId) {
    try {
      ResponseEntity<ViewingRequestList> resp = restClient.get(
          String.format("/viewing-requests/?untreatedByLandlordInFuture=true&userId=%d", userId),
          ViewingRequestList.class
      );
      ViewingRequestList body = resp.getBody();
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
  public ViewingRequest create(long userId, long viewingOfferId, String description) throws Forbidden {
    try {
      User user = userService.get(userId);
      ViewingOffer viewingOffer = viewingOfferService.get(viewingOfferId);
      ViewingRequest viewingRequest = new ViewingRequest();
      viewingRequest.setRequestingUser(user);
      viewingRequest.setRequestedOffer(viewingOffer);
      viewingRequest.setUserDescription(description);

      ResponseEntity<ViewingRequest> resp = restClient.post("/viewing-requests/", viewingRequest, ViewingRequest.class);
      return resp.getBody();
    } catch (RestClientResponseException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new NoSuchElementException("User cannot be found");
      }
      if (e.getStatusCode() == HttpStatus.CONFLICT) {
        throw new Forbidden("User must not request the same offer more than once.");
      }
      throw e;
    }
  }

  @Override
  public boolean hasUserAlreadyRequestedOffer(long userId, long viewingOfferId) {
    try {
      ResponseEntity<Boolean> resp = restClient.get(
          String.format("/viewing-offers/%d/wasRequestedBy?userId=%d", viewingOfferId, userId),
          Boolean.class
      );
      return Boolean.TRUE.equals(resp.getBody());
    } catch (RestClientResponseException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new NoSuchElementException("Viewing request or user cannot be found");
      }
      throw e;
    }
  }

  @Override
  public ViewingRequest decline(long decliningUserId, long viewingRequestId) throws Forbidden {
    try {
      ResponseEntity<ViewingRequest> resp = restClient.post(
          String.format("/viewing-requests/%d/decline?userId=%d", viewingRequestId, decliningUserId),
          null,
          ViewingRequest.class
      );
      return resp.getBody();
    } catch (RestClientResponseException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new NoSuchElementException();
      }
      if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
        throw new Forbidden("");
      }
      throw e;
    }
  }
}
