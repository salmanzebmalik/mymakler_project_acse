package de.unims.acse2024.mymakler.svc.api.service;

import java.util.List;

import de.unims.acse2024.mymakler.svc.api.data.model.ViewingOffer;
import de.unims.acse2024.mymakler.svc.api.service.exception.ViewingOfferDateInPast;

public interface ViewingOfferService {
  ViewingOffer get(long id);

  List<ViewingOffer> getAllInFuture();

  List<ViewingOffer> getAllUntreatedInFuture(long requestingUserId);

  ViewingOffer create(ViewingOffer viewingOffer) throws ViewingOfferDateInPast;
}
