package de.unims.acse2024.mymakler.svc.api.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import de.unims.acse2024.mymakler.svc.api.data.model.MaklerUser;

public interface UserRepository extends JpaRepository<MaklerUser, Long> {
  // Use method name to let Spring generate an appropriate method.
  Optional<MaklerUser> findByUsername(String username);

  boolean existsByUsername(String username);
}
