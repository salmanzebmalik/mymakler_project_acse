package de.unims.acse2024.mymakler.ui.web.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

import de.unims.acse2024.mymakler.ui.web.web.controller.dto.ViewingOfferDto;
import de.unims.acse2024.mymakler.ui.web.web.controller.dto.ViewingRequestDto;
import jakarta.validation.Valid;

import de.unims.acse2024.mymakler.ui.web.service.ViewingOfferService;
import de.unims.acse2024.mymakler.ui.web.service.ViewingRequestService;
import de.unims.acse2024.mymakler.ui.web.service.exception.Forbidden;
import de.unims.acse2024.mymakler.ui.web.service.model.ViewingOffer;
import de.unims.acse2024.mymakler.ui.web.service.model.User;

@Controller
@RequestMapping("/viewing-offers")
public class ViewingOfferController {
  @Autowired
  private ViewingOfferService viewingOfferService;

  @Autowired
  private ViewingRequestService viewingRequestService;

  @GetMapping()
  public String index(@AuthenticationPrincipal User currentUser, Model model) {
    List<ViewingOffer> justUpToDateOffers = viewingOfferService.getAllUntreatedInFuture(currentUser.getId());
    model.addAttribute("offers", justUpToDateOffers);
    return "viewing-offers/index";
  }

  @GetMapping("/{id}")
  public String details(@AuthenticationPrincipal User currentUser, @PathVariable("id") long id, Model model) {
    // Don't allow if already send a request.
    boolean allowToSendRequest = !viewingRequestService.hasUserAlreadyRequestedOffer(currentUser.getId(), id);
    try {
      ViewingOffer viewingOffer = viewingOfferService.get(id);
      model.addAttribute("offer", viewingOffer);
      model.addAttribute("allowToSendApplication", allowToSendRequest);
      if (allowToSendRequest) {
        model.addAttribute("request", new ViewingRequestDto(viewingOffer.getId()));
      }
      return "viewing-offers/details";
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Viewing offer not found", e);
    }
  }

  @PostMapping("/apply")
  public String apply(Model model, @AuthenticationPrincipal User currentUser, @Valid @ModelAttribute("request") ViewingRequestDto viewingRequest, BindingResult result) {
    if (currentUser.getRole().equalsIgnoreCase("LANDLORD")) {
      return "redirect:/";
    }

    if (result.hasErrors()) {
      boolean allowToSendRequest = !viewingRequestService.hasUserAlreadyRequestedOffer(currentUser.getId(), viewingRequest.getOfferId());
      model.addAttribute("offer", viewingOfferService.get(viewingRequest.getOfferId()));
      model.addAttribute("allowToSendApplication", allowToSendRequest);
      if (allowToSendRequest) {
        model.addAttribute("request", viewingRequest);
      }
      return "viewing-offers/details";
    }

    try {
      viewingRequestService.create(currentUser.getId(), viewingRequest.getOfferId(), viewingRequest.getUserDescription());
    } catch (Forbidden e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden action");
    }
    return "redirect:/viewing-offers?successfulRequest";
  }

  @GetMapping("/new")
  public String create(@AuthenticationPrincipal User currentUser, Model model) {
    if (!currentUser.getRole().equalsIgnoreCase("LANDLORD")) {
      return "redirect:/";
    }
    model.addAttribute("offer", new ViewingOfferDto());
    return "viewing-offers/new";
  }

  @PostMapping("/create")
  public String create(@AuthenticationPrincipal User currentUser, @ModelAttribute("offer") @Valid ViewingOfferDto viewingOfferDto, BindingResult result) {
    if (result.hasErrors()) {
      return "viewing-offers/new";
    }

    try {
      ViewingOffer viewingOffer = viewingOfferDto.toViewingOffer();
      viewingOffer.setOfferingUser(currentUser);
      viewingOfferService.create(viewingOffer);
    } catch (IllegalArgumentException e) {
      result.rejectValue("viewingDate", "error.viewingOffer", "Viewing date invalid");
      return "viewing-offers/new";
    }
    return "redirect:/overview";
  }
}
