package de.unims.acse2024.mymakler.ui.web.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import de.unims.acse2024.mymakler.ui.web.service.AppointmentService;
import de.unims.acse2024.mymakler.ui.web.service.model.Appointment;
import de.unims.acse2024.mymakler.ui.web.service.model.User;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {
  @Autowired
  private AppointmentService appointmentService;

  @GetMapping()
  public String index(@AuthenticationPrincipal User currentUser, Model model) {
    List<Appointment> appointmentsForUser = appointmentService.getAllAppointmentsInFutureForUser(currentUser);
    model.addAttribute("appointments", appointmentsForUser);
    return "appointments/index";
  }
}
