package de.unims.acse2024.mymakler.svc.api.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import de.unims.acse2024.mymakler.svc.api.data.model.ViewingOffer;
import de.unims.acse2024.mymakler.svc.api.service.ViewingRequestService;
import de.unims.acse2024.mymakler.svc.api.web.controller.dto.ListDto;
import de.unims.acse2024.mymakler.svc.api.web.controller.dto.ViewingOfferDto;
import de.unims.acse2024.mymakler.svc.api.service.ViewingOfferService;
import de.unims.acse2024.mymakler.svc.api.service.exception.ViewingOfferDateInPast;

@RestController
@RequestMapping("/viewing-offers")
public class ViewingOfferRestController {
  @Autowired
  private ViewingOfferService viewingOfferService;

  @Autowired
  private ViewingRequestService viewingRequestService;

  @GetMapping("/{id}")
  public ResponseEntity<ViewingOfferDto> get(@PathVariable("id") long id) {
    try {
      ViewingOffer viewingOffer = viewingOfferService.get(id);
      return ResponseEntity.ok(new ViewingOfferDto(viewingOffer));
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/{id}/wasRequestedBy")
  public ResponseEntity<Boolean> get(@PathVariable("id") long id, @RequestParam("userId") long userId) {
    try {
      Boolean result = viewingRequestService.hasUserAlreadyRequestedOffer(userId, id);
      return ResponseEntity.ok(result);
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/")
  public ResponseEntity<ListDto<ViewingOfferDto>> getAll(@RequestParam(value = "userId", required = false) Long userId,
      @RequestParam(value = "untreatedInFuture", required = false) Boolean untreatedInFuture) {
    try {
      List<ViewingOffer> viewingOffers;

      // filter by untreated in future for specific user
      if (untreatedInFuture != null && untreatedInFuture) {
        if (userId == null) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        viewingOffers = viewingOfferService.getAllUntreatedInFuture(userId);
      }
      // don't filter and return all in future
      else {
        viewingOffers = viewingOfferService.getAllInFuture();
      }

      var list = new ListDto<>(
          viewingOffers.stream()
              .map(ViewingOfferDto::new)
              .collect(Collectors.toList())
      );
      return ResponseEntity.ok(list);
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/")
  public ResponseEntity<ViewingOfferDto> create(@RequestBody ViewingOfferDto viewingOffer) {
    try {
      ViewingOffer vo = viewingOfferService.create(viewingOffer.toViewingOffer());
      return ResponseEntity.status(HttpStatus.CREATED).body(new ViewingOfferDto(vo));
    } catch (ViewingOfferDateInPast e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
