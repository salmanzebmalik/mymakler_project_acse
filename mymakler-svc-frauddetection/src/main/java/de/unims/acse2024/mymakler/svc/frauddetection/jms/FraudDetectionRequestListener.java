package de.unims.acse2024.mymakler.svc.frauddetection.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import de.unims.acse2024.mymakler.svc.frauddetection.service.FraudDetectionService;
import de.unims.acse2024.mymakler.svc.frauddetection.service.model.ViewingOfferDto;

@Component
public class FraudDetectionRequestListener {
  @Autowired
  private FraudDetectionService fraudDetectionService;

  @JmsListener(destination = "new_viewing_offer")
  public void handleNewViewingOffer(ViewingOfferDto viewingOffer) {
    fraudDetectionService.checkViewingOffer(viewingOffer);
  }
}
