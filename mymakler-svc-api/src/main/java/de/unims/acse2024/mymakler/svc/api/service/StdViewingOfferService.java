package de.unims.acse2024.mymakler.svc.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import de.unims.acse2024.mymakler.svc.api.data.model.MaklerUser;
import de.unims.acse2024.mymakler.svc.api.data.model.ViewingOffer;
import de.unims.acse2024.mymakler.svc.api.data.repo.ViewingOfferRepository;
import de.unims.acse2024.mymakler.svc.api.service.exception.ViewingOfferDateInPast;
import de.unims.acse2024.mymakler.svc.api.web.controller.dto.ViewingOfferDto;

@Service
public class StdViewingOfferService implements ViewingOfferService {
  @Autowired
  private JmsTemplate template;

  @Value("${mymakler.jms.queues.new_viewing_offer}")
  private String queueName;

  @Autowired
  private UserService userService;

  @Autowired
  private ViewingOfferRepository viewingOfferRepository;

  @Override
  public ViewingOffer get(long id) {
    return viewingOfferRepository.findById(id).orElseThrow();
  }

  @Override
  public List<ViewingOffer> getAllInFuture() {
    return viewingOfferRepository.findAllViewingOffersInFuture(LocalDateTime.now());
  }

  @Override
  public List<ViewingOffer> getAllUntreatedInFuture(long userId) {
    return viewingOfferRepository.findAllViewingOffersInFutureUntreatedByRequestingUser(LocalDateTime.now(), userId);
  }

  @Override
  public ViewingOffer create(ViewingOffer viewingOffer) throws ViewingOfferDateInPast {
    if (viewingOffer.getViewingDate().isBefore(LocalDateTime.now())) {
      throw new ViewingOfferDateInPast();
    }
    viewingOffer = viewingOfferRepository.save(viewingOffer);
    // announce creation in JMS queue
    template.convertAndSend(queueName, new ViewingOfferDto(viewingOffer));
    return viewingOffer;
  }
}
