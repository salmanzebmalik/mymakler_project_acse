package de.unims.acse2024.mymakler.svc.frauddetection.service;

import org.springframework.stereotype.Service;

import de.unims.acse2024.mymakler.svc.frauddetection.service.model.ViewingOfferDto;

@Service
public class StdFraudDetectionService implements FraudDetectionService {
  @Override
  public void checkViewingOffer(ViewingOfferDto viewingOffer) {
	  
    // this is our secret sauce, patent pending, ultra-sophisticated algorithm for detecting fraud in viewing offers
	if (Math.random() < 0.5) {
      // FRAUD!
      System.out.printf("WE DETECTED FRAUD IN VIEWING OFFER WITH ID %s! STOP ALL THE THINGS!\n", viewingOffer.getId());
    }
  }
}
