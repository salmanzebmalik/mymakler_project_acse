package de.unims.acse2024.mymakler.svc.api.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.NoSuchElementException;

import de.unims.acse2024.mymakler.svc.api.data.model.MaklerUser;
import de.unims.acse2024.mymakler.svc.api.web.controller.dto.UserDto;
import de.unims.acse2024.mymakler.svc.api.service.UserService;
import de.unims.acse2024.mymakler.svc.api.service.exception.UsernameTaken;

@RestController
@RequestMapping("/users")
public class UserRestController {
  @Autowired
  private UserService userService;

  @GetMapping("/")
  public ResponseEntity<UserDto> get(@RequestParam(value = "id", required = false) Long id,
      @RequestParam(value = "username", required = false) String username) {
    try {
      MaklerUser u;
      if (id != null) {
        u = userService.get(id);
      } else if (username != null) {
        u = userService.getByUsername(username);
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      }
      return ResponseEntity.ok(new UserDto(u));
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/")
  public ResponseEntity<UserDto> create(@RequestBody UserDto user) {
    try {
      MaklerUser u = userService.create(user.toUser());
      return ResponseEntity.status(HttpStatus.CREATED).body(new UserDto(u));
    } catch (UsernameTaken e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDto> update(@PathVariable("id") Long id, @RequestBody UserDto user) {
    try {
      if (id != user.getId()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      }

      MaklerUser u = user.toUser();
      u.setId(id);
      u = userService.update(u);
      return ResponseEntity.ok(new UserDto(u));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
