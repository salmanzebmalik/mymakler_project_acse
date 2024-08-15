package de.unims.acse2024.mymakler.ui.web.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import de.unims.acse2024.mymakler.ui.web.service.model.User;
import de.unims.acse2024.mymakler.ui.web.service.exception.ConfirmationFailed;
import de.unims.acse2024.mymakler.ui.web.service.exception.PasswordNotLongEnough;
import de.unims.acse2024.mymakler.ui.web.service.exception.UsernameTaken;

public interface UserService extends UserDetailsService {
  void login(User user);

  User get(long id);

  User getByUsername(String username);

  User create(User user) throws UsernameTaken, PasswordNotLongEnough;

  User update(User user) throws PasswordNotLongEnough, ConfirmationFailed;
}
