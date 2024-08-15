package de.unims.acse2024.mymakler.svc.api.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import de.unims.acse2024.mymakler.svc.api.data.model.Appointment;
import de.unims.acse2024.mymakler.svc.api.data.model.MaklerUser;
import de.unims.acse2024.mymakler.svc.api.web.controller.dto.AppointmentDto;
import de.unims.acse2024.mymakler.svc.api.web.controller.dto.ListDto;
import de.unims.acse2024.mymakler.svc.api.service.AppointmentService;
import de.unims.acse2024.mymakler.svc.api.service.UserService;

@RestController
@RequestMapping("/appointments")
public class AppointmentRestController {
  @Autowired
  private AppointmentService appointmentService;

  @Autowired
  private UserService userService;

  @GetMapping("/")
  public ResponseEntity<ListDto<AppointmentDto>> getAll(@RequestParam(value = "userId", required = false) Long userId) {
    try {
      List<Appointment> appointments;

      // filter by in future for specific user
      if (userId != null) {
        MaklerUser user = userService.get(userId);
        appointments = appointmentService.getAllAppointmentsInFutureForUser(user);
      }
      // no other filter supported
      else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      }

      var list = new ListDto<>(
          appointments.stream()
              .map(AppointmentDto::new)
              .collect(Collectors.toList())
      );
      return ResponseEntity.ok(list);
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
