package de.unims.acse2024.mymakler.ui.web.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class User implements Serializable, UserDetails {
  private static final long serialVersionUID = 15962998226472476L;

  private long id;

  @NotBlank
  private String username;

  private String password;

  private String plaintextPassword;

  private String confirmationPassword;

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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @JsonIgnore
  public String getPlaintextPassword() {
    return plaintextPassword;
  }

  @JsonIgnore
  public void setPlaintextPassword(String plaintextPassword) {
    this.plaintextPassword = plaintextPassword;
  }

  @JsonIgnore
  public String getConfirmationPassword() {
    return confirmationPassword;
  }

  @JsonIgnore
  public void setConfirmationPassword(String confirmationPassword) {
    this.confirmationPassword = confirmationPassword;
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

  @JsonIgnore
  public String getRegisteredSince() {
    return registeredSince;
  }

  @JsonProperty
  public void setRegisteredSince(String registeredSince) {
    this.registeredSince = registeredSince;
  }

  @JsonIgnore
  public String getLastModified() {
    return lastModified;
  }

  @JsonProperty
  public void setLastModified(String lastModified) {
    this.lastModified = lastModified;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(
        new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())
    );
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
