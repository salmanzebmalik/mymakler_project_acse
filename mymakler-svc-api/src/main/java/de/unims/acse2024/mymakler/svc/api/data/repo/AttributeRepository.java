package de.unims.acse2024.mymakler.svc.api.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import de.unims.acse2024.mymakler.svc.api.data.model.Attribute;

public interface AttributeRepository extends JpaRepository<Attribute, String> {
}
