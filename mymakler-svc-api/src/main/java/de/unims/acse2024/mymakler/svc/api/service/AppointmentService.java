package de.unims.acse2024.mymakler.svc.api.service;

import java.util.List;

import de.unims.acse2024.mymakler.svc.api.data.model.Appointment;
import de.unims.acse2024.mymakler.svc.api.data.model.MaklerUser;
import de.unims.acse2024.mymakler.svc.api.service.exception.Forbidden;

public interface AppointmentService {
  List<Appointment> getAllAppointmentsInFutureForUser(MaklerUser user);

  Appointment create(long acceptingUserId, long requestId, String remarks) throws Forbidden;
}
