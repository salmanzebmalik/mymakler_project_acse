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

import de.unims.acse2024.mymakler.svc.api.data.model.Appointment;
import de.unims.acse2024.mymakler.svc.api.data.model.ViewingRequest;
import de.unims.acse2024.mymakler.svc.api.web.controller.dto.AppointmentDto;
import de.unims.acse2024.mymakler.svc.api.web.controller.dto.ListDto;
import de.unims.acse2024.mymakler.svc.api.web.controller.dto.ViewingRequestDto;
import de.unims.acse2024.mymakler.svc.api.service.AppointmentService;
import de.unims.acse2024.mymakler.svc.api.service.ViewingRequestService;
import de.unims.acse2024.mymakler.svc.api.service.exception.Forbidden;

@RestController
@RequestMapping("/viewing-requests")
public class ViewingRequestRestController {
  @Autowired
  private ViewingRequestService viewingRequestService;

  @Autowired
  private AppointmentService appointmentService;

  @GetMapping("/{id}")
  public ResponseEntity<ViewingRequestDto> get(@PathVariable("id") long id) {
    try {
      ViewingRequest viewingRequest = viewingRequestService.get(id);
      return ResponseEntity.ok(new ViewingRequestDto(viewingRequest));
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/")
  public ResponseEntity<ListDto<ViewingRequestDto>> getAll(@RequestParam(value = "userId", required = false) Long userId,
      @RequestParam(value = "untreatedByLandlordInFuture", required = false) Boolean untreatedByLandlordInFuture) {
    try {
      List<ViewingRequest> viewingRequests;

      // filter by untreated by landlord in future for specific user
      if (untreatedByLandlordInFuture != null && untreatedByLandlordInFuture) {
        if (userId == null) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        viewingRequests = viewingRequestService.getAllUntreatedByLandlordInFuture(userId);
      }
      // no other filter supported
      else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      }

      var list = new ListDto<>(
          viewingRequests.stream()
              .map(ViewingRequestDto::new)
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
  public ResponseEntity<ViewingRequestDto> create(@RequestBody ViewingRequestDto viewingRequestDto) {
    try {
      ViewingRequest viewingRequest = viewingRequestService.create(
          viewingRequestDto.getRequestingUser().getId(),
          viewingRequestDto.getRequestedOffer().getId(),
          viewingRequestDto.getUserDescription()
      );
      return ResponseEntity.status(HttpStatus.CREATED).body(new ViewingRequestDto(viewingRequest));
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Forbidden e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/{id}/accept")
  public ResponseEntity<AppointmentDto> acceptViewingRequest(@PathVariable("id") long id, @RequestBody AppointmentDto appointment) {
    try {
      Appointment a = appointmentService.create(
          appointment.getRequest().getRequestedOffer().getOfferingUser().getId(),
          id,
          appointment.getOfferingUserRemarks()
      );
      return ResponseEntity.status(HttpStatus.CREATED).body(new AppointmentDto(a));
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Forbidden e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/{id}/decline")
  public ResponseEntity<ViewingRequestDto> declineViewingRequest(@PathVariable("id") long id, @RequestParam("userId") Long userId) {
    try {
      ViewingRequest viewingRequest = viewingRequestService.decline(userId, id);
      return ResponseEntity.ok(new ViewingRequestDto(viewingRequest));
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Forbidden e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
