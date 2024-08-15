package de.unims.acse2024.mymakler.svc.api.service;

import java.util.List;

import de.unims.acse2024.mymakler.svc.api.data.model.ViewingRequest;
import de.unims.acse2024.mymakler.svc.api.service.exception.Forbidden;

public interface ViewingRequestService {
  ViewingRequest get(long id);

  List<ViewingRequest> getAllUntreatedByLandlordInFuture(long userId);

  ViewingRequest create(long userId, long viewingOfferId, String description) throws Forbidden;

  boolean hasUserAlreadyRequestedOffer(long userId, long viewingOfferId);

  ViewingRequest decline(long decliningUserId, long viewingRequestId) throws Forbidden;
}
