package de.unims.acse2024.mymakler.svc.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import de.unims.acse2024.mymakler.svc.api.data.model.ViewingRequest;
import de.unims.acse2024.mymakler.svc.api.data.repo.ViewingRequestRepository;
import de.unims.acse2024.mymakler.svc.api.service.exception.Forbidden;

@Service
public class StdViewingRequestService implements ViewingRequestService {
  @Autowired
  private ViewingRequestRepository viewingRequestRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private ViewingOfferService viewingOfferService;

  @Override
  public ViewingRequest get(long id) {
    return viewingRequestRepository.findById(id).orElseThrow();
  }

  @Override
  public List<ViewingRequest> getAllUntreatedByLandlordInFuture(long userId) {
    return viewingRequestRepository.findAllUntreatedByLandlordWithViewingOfferAfter(userId, LocalDateTime.now());
  }

  @Override
  public ViewingRequest create(long userId, long viewingOfferId, String description) throws Forbidden {
    if (hasUserAlreadyRequestedOffer(userId, viewingOfferId)) {
      throw new Forbidden("User must not request the same offer more than once.");
    }

    ViewingRequest viewingRequest = new ViewingRequest();
    viewingRequest.setRequestingUser(userService.get(userId));
    viewingRequest.setRequestedOffer(viewingOfferService.get(viewingOfferId));
    viewingRequest.setUserDescription(description);
    return viewingRequestRepository.save(viewingRequest);
  }

  @Override
  public boolean hasUserAlreadyRequestedOffer(long userId, long viewingOfferId) {
    return viewingRequestRepository.hasUserAlreadyRequestedViewingOffer(userId, viewingOfferId);
  }

  @Override
  public ViewingRequest decline(long decliningUserId, long viewingRequestId) throws Forbidden {
    ViewingRequest toDecline = viewingRequestRepository.findById(viewingRequestId).orElseThrow();
    if (toDecline.getRequestedOffer().getOfferingUser().getId() != decliningUserId) {
      throw new Forbidden("Offering user does not match current user.");
    }
    toDecline.setWasDeclined(true);
    return viewingRequestRepository.save(toDecline);
  }
}
