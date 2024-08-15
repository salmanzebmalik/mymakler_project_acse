package de.unims.acse2024.mymakler.svc.api.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import de.unims.acse2024.mymakler.svc.api.data.model.Attribute;
import de.unims.acse2024.mymakler.svc.api.data.model.MaklerUser;
import de.unims.acse2024.mymakler.svc.api.data.model.Role;

public class UserDto implements Serializable {
  private static final long serialVersionUID = 15962998226472476L;

  private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

  private long id;

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @Pattern(regexp = "[a-zA-Z, ]+")
  private String attributes;

  @NotBlank
  private String role;

  @NotBlank
  private String cityOfOrigin;

  @NotBlank
  @Email
  private String email;

  private String registeredSince;

  private String lastModified;

  public UserDto() {}

  public UserDto(MaklerUser user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.password = user.getPassword();
    this.attributes = user.getAttributes().stream()
        .map(Attribute::getName)
        .sorted()
        .collect(Collectors.joining(", "));
    this.role = user.getRole().toString();
    this.cityOfOrigin = user.getCityOfOrigin();
    if (user.getRegisteredSince() != null) {
      this.registeredSince = user.getRegisteredSince().format(dateTimeFormatter);
    }
    if (user.getLastModified() != null) {
      this.lastModified = user.getLastModified().format(dateTimeFormatter);
    }
    this.email = user.getEmail();
  }

  public MaklerUser toUser() {
    MaklerUser user = new MaklerUser();
    user.setId(id);
    user.setUsername(username);
    user.setPassword(password);
    user.setAttributes(Attribute.of(attributes.replaceAll(" ", "").split(",")));
    user.setRole(Role.of(role));
    user.setCityOfOrigin(cityOfOrigin);
    user.setEmail(email);
    if (registeredSince != null) {
      user.setRegisteredSince(LocalDateTime.parse(registeredSince, dateTimeFormatter));
    }
    if (lastModified != null) {
      user.setLastModified(LocalDateTime.parse(lastModified, dateTimeFormatter));
    }
    return user;
  }

  public long getId() {
    return id;
  }

  public void setId(@NotNull long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAttributes() {
    return attributes;
  }

  public void setAttributes(String attributes) {
    this.attributes = attributes;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getCityOfOrigin() {
    return cityOfOrigin;
  }

  public void setCityOfOrigin(String cityOfOrigin) {
    this.cityOfOrigin = cityOfOrigin;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @JsonProperty
  public String getRegisteredSince() {
    return registeredSince;
  }

  @JsonIgnore
  public void setRegisteredSince(String registeredSince) {
    this.registeredSince = registeredSince;
  }

  @JsonProperty
  public String getLastModified() {
    return lastModified;
  }

  @JsonIgnore
  public void setLastModified(String lastModified) {
    this.lastModified = lastModified;
  }
}
