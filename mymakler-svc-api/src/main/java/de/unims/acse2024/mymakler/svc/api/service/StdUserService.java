package de.unims.acse2024.mymakler.svc.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import de.unims.acse2024.mymakler.svc.api.data.model.MaklerUser;
import de.unims.acse2024.mymakler.svc.api.data.repo.UserRepository;
import de.unims.acse2024.mymakler.svc.api.service.exception.UsernameTaken;

@Service
public class StdUserService implements UserService {
  @Autowired
  private AttributeService attributeService;

  @Autowired
  private UserRepository userRepository;

  @Override
  public MaklerUser get(long id) {
    return userRepository.findById(id).orElseThrow();
  }

  @Override
  public MaklerUser getByUsername(String username) {
    return userRepository.findByUsername(username).orElseThrow();
  }

  @Override
  public MaklerUser create(MaklerUser user) throws UsernameTaken {
    if (userRepository.existsByUsername(user.getUsername())) {
      throw new UsernameTaken(String.format("The given username %s is already taken.", user.getUsername()));
    }
    user.setAttributes(new HashSet<>(attributeService.createOrGet(user.getAttributes())));
    return userRepository.save(user);
  }

  @Override
  public MaklerUser update(MaklerUser newUser) {
    // only allow update of certain fields
    MaklerUser user = userRepository.findById(newUser.getId()).orElseThrow();

    if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
      user.setPassword(newUser.getPassword());
    }
    user.setAttributes(new HashSet<>(attributeService.createOrGet(newUser.getAttributes())));
    user.setCityOfOrigin(newUser.getCityOfOrigin());
    user.setEmail(newUser.getEmail());

    return userRepository.save(user);
  }
}
