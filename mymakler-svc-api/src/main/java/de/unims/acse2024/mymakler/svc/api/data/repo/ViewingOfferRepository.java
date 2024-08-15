package de.unims.acse2024.mymakler.svc.api.data.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.unims.acse2024.mymakler.svc.api.data.model.ViewingOffer;

public interface ViewingOfferRepository extends JpaRepository<ViewingOffer, Long> {
  // We do not have to write an implementation class; this query can directly be called using this interface
  @Query("SELECT v "
      + "FROM ViewingOffer v "
      + "WHERE v.viewingDate > :afterThisDate "
      + "ORDER BY v.onlineSince DESC")
  List<ViewingOffer> findAllViewingOffersInFuture(
      @Param("afterThisDate") LocalDateTime afterThisDate
  );

  @Query("SELECT vo "
      + "FROM ViewingOffer vo "
      + "WHERE vo.id NOT IN ("
      + "  SELECT voo.id "
      + "  FROM ViewingOffer voo "
      + "  JOIN voo.requestsForOffer vr "
      + "  WHERE vr.requestingUser.id = :userId"
      + ")"
      + "AND vo.viewingDate > :afterThisDate "
      + "ORDER BY vo.onlineSince DESC")
  List<ViewingOffer> findAllViewingOffersInFutureUntreatedByRequestingUser(
      @Param("afterThisDate") LocalDateTime afterThisDate,
      @Param("userId") long userId
  );
}
