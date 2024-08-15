package de.unims.acse2024.mymakler.ui.web.service;

import java.util.List;

import de.unims.acse2024.mymakler.ui.web.service.model.ViewingOffer;

public interface ViewingOfferService {
  ViewingOffer get(long id);

  List<ViewingOffer> getAllInFuture();

  List<ViewingOffer> getAllUntreatedInFuture(long requestingUserId);

  ViewingOffer create(ViewingOffer viewingOffer);
}
