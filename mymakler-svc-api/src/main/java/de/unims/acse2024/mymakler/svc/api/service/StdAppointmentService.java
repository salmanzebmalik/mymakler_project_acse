package de.unims.acse2024.mymakler.svc.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import de.unims.acse2024.mymakler.svc.api.data.model.Appointment;
import de.unims.acse2024.mymakler.svc.api.data.model.MaklerUser;
import de.unims.acse2024.mymakler.svc.api.data.model.Role;
import de.unims.acse2024.mymakler.svc.api.data.model.ViewingRequest;
import de.unims.acse2024.mymakler.svc.api.data.repo.AppointmentRepository;
import de.unims.acse2024.mymakler.svc.api.service.exception.Forbidden;

@Service
public class StdAppointmentService implements AppointmentService {
  @Autowired
  private AppointmentRepository appointmentRepository;

  @Autowired
  private ViewingRequestService viewingRequestService;

  private List<Appointment> getAllAppointmentsInFutureForOfferingUser(long userId) {
    return appointmentRepository.findAllAppointmentsInFutureForOfferingUser(LocalDateTime.now(), userId);
  }

  private List<Appointment> getAllAppointmentsInFutureForRequestingUser(long userId) {
    return appointmentRepository.findAllAppointmentsInFutureForRequestingUser(LocalDateTime.now(), userId);
  }

  @Override
  public List<Appointment> getAllAppointmentsInFutureForUser(MaklerUser user) {
    if (user.getRole() == Role.LANDLORD) {
      return getAllAppointmentsInFutureForOfferingUser(user.getId());
    } else {
      return getAllAppointmentsInFutureForRequestingUser(user.getId());
    }
  }

  @Override
  public Appointment create(long acceptingUserId, long viewingRequestId, String remarks) throws Forbidden {
    Appointment appointment = new Appointment();
    ViewingRequest viewingRequest = viewingRequestService.get(viewingRequestId);
    if (viewingRequest.getRequestedOffer().getOfferingUser().getId() != acceptingUserId) {
      throw new Forbidden("The accepting user is not the offering user");
    }
    if (viewingRequest.isWasDeclined()) {
      throw new Forbidden("Request was already declined");
    }
    if (viewingRequest.getAppointment() != null) {
      throw new Forbidden("There already is an appointment for this request");
    }
    appointment.setRequest(viewingRequest);
    appointment.setOfferingUserRemarks(remarks);

    return appointmentRepository.save(appointment);
  }
}
