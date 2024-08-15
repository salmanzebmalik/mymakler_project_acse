package de.unims.acse2024.mymakler.ui.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import de.unims.acse2024.mymakler.ui.web.service.model.Appointment;
import de.unims.acse2024.mymakler.ui.web.service.model.AppointmentList;
import de.unims.acse2024.mymakler.ui.web.service.model.User;
import de.unims.acse2024.mymakler.ui.web.service.exception.Forbidden;
import de.unims.acse2024.mymakler.ui.web.service.model.ViewingRequest;

@Service
public class RestAppointmentService implements AppointmentService {
  @Autowired
  RestClient restClient;

  @Autowired
  ViewingRequestService viewingRequestService;

  @Override
  public List<Appointment> getAllAppointmentsInFutureForUser(User user) {
    try {
      ResponseEntity<AppointmentList> resp = restClient.get(
          String.format("/appointments/?userId=%d", user.getId()),
          AppointmentList.class
      );
      AppointmentList body = resp.getBody();
      if (body == null) {
        return Collections.emptyList();
      }
      return body.getItems();
    } catch (RestClientResponseException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new NoSuchElementException("User cannot be found");
      }
      throw e;
    }
  }

  @Override
  public Appointment create(long acceptingUserId, long viewingRequestId, String remarks) throws Forbidden {
    try {
      ViewingRequest viewingRequest = viewingRequestService.get(viewingRequestId);
      Appointment appointment = new Appointment();
      appointment.setRequest(viewingRequest);
      appointment.setOfferingUserRemarks(remarks);
      ResponseEntity<Appointment> resp = restClient.post(
          String.format("/viewing-requests/%d/accept", viewingRequestId),
          appointment,
          Appointment.class
      );
      return resp.getBody();
    } catch (RestClientResponseException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new NoSuchElementException();
      }
      if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
        throw new Forbidden("");
      }
      throw e;
    }
  }
}
