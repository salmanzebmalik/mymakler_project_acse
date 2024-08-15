package de.unims.acse2024.mymakler.svc.api.data.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.unims.acse2024.mymakler.svc.api.data.model.ViewingRequest;

public interface ViewingRequestRepository extends JpaRepository<ViewingRequest, Long> {
  @Query("SELECT vr FROM ViewingRequest vr "
      + "LEFT OUTER JOIN Appointment a ON vr.id = a.request.id "
      + "WHERE a.id IS NULL "
      + "AND vr.requestedOffer.viewingDate > :afterDate "
      + "AND vr.requestedOffer.offeringUser.id = :userId "
      + "AND vr.wasDeclined = FALSE "
      + "ORDER BY vr.requestedOffer.viewingDate ASC")
  List<ViewingRequest> findAllUntreatedByLandlordWithViewingOfferAfter(
      @Param("userId") long offeringUserId,
      @Param("afterDate") LocalDateTime afterDate
  );

  @Query("SELECT CASE "
      + "WHEN COUNT(vr) > 0 "
      + "THEN TRUE "
      + "ELSE FALSE "
      + "END "
      + "FROM ViewingRequest vr "
      + "WHERE vr.requestingUser.id = :userId "
      + "AND vr.requestedOffer.id = :viewingOfferId")
  boolean hasUserAlreadyRequestedViewingOffer(
      @Param("userId") long requestingUserId,
      @Param("viewingOfferId") long viewingOfferId
  );
}
