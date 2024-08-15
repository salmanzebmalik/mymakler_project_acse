package de.unims.acse2024.mymakler.ui.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import de.unims.acse2024.mymakler.ui.web.service.model.User;
import de.unims.acse2024.mymakler.ui.web.service.exception.ConfirmationFailed;
import de.unims.acse2024.mymakler.ui.web.service.exception.PasswordNotLongEnough;
import de.unims.acse2024.mymakler.ui.web.service.exception.UsernameTaken;

@Service
public class RestUserService implements UserService {
  private static final int MIN_PASSWORD_SIZE = 3;

  @Autowired
  RestClient restClient;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      return getByUsername(username);
    } catch (RestClientException e) {
      throw new UsernameNotFoundException("error when loading user from API", e);
    }
  }

  @Override
  public void login(User user) {
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(token);
  }

  @Override
  public User get(long id) {
    ResponseEntity<User> resp = restClient.get(String.format("/users/?id=%d", id), User.class);
    return resp.getBody();
  }

  private void validatePassword(String password) throws PasswordNotLongEnough {
    if (password == null || password.length() < MIN_PASSWORD_SIZE) {
      throw new PasswordNotLongEnough();
    }
  }

  @Override
  public User getByUsername(String username) {
    ResponseEntity<User> resp = restClient.get(String.format("/users/?username=%s", username), User.class);
    return resp.getBody();
  }

  @Override
  public User create(User user) throws UsernameTaken, PasswordNotLongEnough {
    try {
      validatePassword(user.getPlaintextPassword());
      replacePlaintextPassword(user);
      ResponseEntity<User> resp = restClient.post("/users/", user, User.class);
      return resp.getBody();
    } catch (RestClientResponseException e) {
      if (e.getStatusCode() == HttpStatus.CONFLICT) {
        throw new UsernameTaken(String.format("The given username %s is already taken.", user.getUsername()));
      }
      throw e;
    }
  }

  @Override
  public User update(User updatedUser) throws PasswordNotLongEnough, ConfirmationFailed {
    // We must update the old version by setting its values according to 'updatedUser'
    User oldUser = get(updatedUser.getId());

    if (!passwordEncoder.matches(updatedUser.getConfirmationPassword(), oldUser.getPassword())) {
      throw new ConfirmationFailed();
    }

    if (updatedUser.getPlaintextPassword() != null && !updatedUser.getPlaintextPassword().isEmpty()) {
      validatePassword(updatedUser.getPlaintextPassword());
      replacePlaintextPassword(updatedUser);
      oldUser.setPassword(updatedUser.getPassword());
    }

    oldUser.setAttributes(updatedUser.getAttributes());
    oldUser.setCityOfOrigin(updatedUser.getCityOfOrigin());
    oldUser.setEmail(updatedUser.getEmail());

    ResponseEntity<User> resp = restClient.put(String.format("/users/%d", updatedUser.getId()), oldUser, User.class);
    return resp.getBody();
  }

  private void replacePlaintextPassword(User userWithPlaintextPassword) {
    String result = passwordEncoder.encode(userWithPlaintextPassword.getPlaintextPassword());
    userWithPlaintextPassword.setPlaintextPassword(null);
    userWithPlaintextPassword.setPassword(result);
  }
}
