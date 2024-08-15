package de.unims.acse2024.mymakler.svc.api.service;

import java.util.List;
import java.util.Set;

import de.unims.acse2024.mymakler.svc.api.data.model.Attribute;

public interface AttributeService {
  List<Attribute> getAllAttributes();

  Set<Attribute> createOrGet(Set<Attribute> attributes);
}
