package de.unims.acse2024.mymakler.svc.frauddetection.service;

import de.unims.acse2024.mymakler.svc.frauddetection.service.model.ViewingOfferDto;

public interface FraudDetectionService {
  void checkViewingOffer(ViewingOfferDto viewingOffer);
}
