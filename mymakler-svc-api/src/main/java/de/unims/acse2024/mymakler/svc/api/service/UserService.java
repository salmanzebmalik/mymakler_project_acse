package de.unims.acse2024.mymakler.svc.api.service;

import de.unims.acse2024.mymakler.svc.api.data.model.MaklerUser;
import de.unims.acse2024.mymakler.svc.api.service.exception.UsernameTaken;

public interface UserService {
  MaklerUser get(long id);

  MaklerUser getByUsername(String username);

  MaklerUser create(MaklerUser user) throws UsernameTaken;

  MaklerUser update(MaklerUser user);
}
