package de.unims.acse2024.mymakler.ui.web.service;

import java.util.List;

import de.unims.acse2024.mymakler.ui.web.service.model.Appointment;
import de.unims.acse2024.mymakler.ui.web.service.model.User;
import de.unims.acse2024.mymakler.ui.web.service.exception.Forbidden;

public interface AppointmentService {
  List<Appointment> getAllAppointmentsInFutureForUser(User user);

  Appointment create(long acceptingUserId, long requestId, String remarks) throws Forbidden;
}
