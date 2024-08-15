package de.unims.acse2024.mymakler.svc.frauddetection.service.model;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UserDto implements Serializable {
  private static final long serialVersionUID = 15962998226472476L;

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

  public String getRegisteredSince() {
    return registeredSince;
  }

  public void setRegisteredSince(String registeredSince) {
    this.registeredSince = registeredSince;
  }

  public String getLastModified() {
    return lastModified;
  }

  public void setLastModified(String lastModified) {
    this.lastModified = lastModified;
  }
}
