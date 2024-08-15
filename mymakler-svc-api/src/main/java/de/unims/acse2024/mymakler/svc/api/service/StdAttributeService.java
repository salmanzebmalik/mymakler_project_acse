package de.unims.acse2024.mymakler.svc.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.unims.acse2024.mymakler.svc.api.data.model.Attribute;
import de.unims.acse2024.mymakler.svc.api.data.repo.AttributeRepository;

@Service
public class StdAttributeService implements AttributeService {
  @Autowired
  private AttributeRepository attributeRepository;

  @Override
  public List<Attribute> getAllAttributes() {
    return attributeRepository.findAll();
  }

  @Override
  public Set<Attribute> createOrGet(Set<Attribute> attributes) {
    return new HashSet<>(attributeRepository.saveAll(attributes));
  }
}
