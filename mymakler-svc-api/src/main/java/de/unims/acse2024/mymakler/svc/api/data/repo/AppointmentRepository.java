package de.unims.acse2024.mymakler.svc.api.data.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.unims.acse2024.mymakler.svc.api.data.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
  @Query("SELECT a "
      + "FROM Appointment a "
      + "JOIN a.request vr "
      + "JOIN vr.requestedOffer vo "
      + "WHERE vo.offeringUser.id = :userId "
      + "AND vo.viewingDate > :afterThisDate "
      + "ORDER BY vo.viewingDate ASC")
  List<Appointment> findAllAppointmentsInFutureForOfferingUser(
      @Param("afterThisDate") LocalDateTime afterThisDate,
      @Param("userId") long userId
  );

  @Query("SELECT a "
      + "FROM Appointment a "
      + "WHERE a.request.requestingUser.id = :userId "
      + "AND a.request.requestedOffer.viewingDate > :afterThisDate "
      + "ORDER BY a.request.requestedOffer.viewingDate ASC")
  List<Appointment> findAllAppointmentsInFutureForRequestingUser(
    @Param("afterThisDate") LocalDateTime afterThisDate,
    @Param("userId") long userId
  );
}
