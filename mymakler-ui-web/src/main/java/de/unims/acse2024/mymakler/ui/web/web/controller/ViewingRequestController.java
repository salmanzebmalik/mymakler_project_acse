package de.unims.acse2024.mymakler.ui.web.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

import de.unims.acse2024.mymakler.ui.web.service.AppointmentService;
import de.unims.acse2024.mymakler.ui.web.service.ViewingRequestService;
import de.unims.acse2024.mymakler.ui.web.service.exception.Forbidden;
import de.unims.acse2024.mymakler.ui.web.service.model.User;
import de.unims.acse2024.mymakler.ui.web.service.model.ViewingRequest;

@Controller
@RequestMapping("/viewing-requests")
public class ViewingRequestController {
  @Autowired
  private ViewingRequestService viewingRequestService;

  @Autowired
  private AppointmentService appointmentService;

  @GetMapping()
  public String index(@AuthenticationPrincipal User currentUser, Model model) {
    List<ViewingRequest> untreated = viewingRequestService.getAllUntreatedByLandlordInFuture(currentUser.getId());
    model.addAttribute("untreated", untreated);
    return "viewing-requests/index";
  }

  @PostMapping("/accept")
  public String accept(Model model, @AuthenticationPrincipal User currentUser, String offeringUserRemarks, long requestId) {
    try {
      appointmentService.create(currentUser.getId(), requestId, offeringUserRemarks);
    } catch (Forbidden e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden action");
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Viewing request cannot be found");
    }
    return "redirect:/viewing-requests?accepted";
  }

  @PostMapping("/decline")
  public String decline(long requestId, @AuthenticationPrincipal User currentUser) {
    try {
      viewingRequestService.decline(currentUser.getId(), requestId);
    } catch (Forbidden e) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden action");
    }
    return "redirect:/viewing-requests?declined";
  }
}
