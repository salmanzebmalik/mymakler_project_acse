package de.unims.acse2024.mymakler.svc.api.data.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class MaklerUser implements Serializable {
  private static final long serialVersionUID = -2002616133498412128L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NotBlank
  @Column(unique = true)
  private String username;

  @NotBlank
  private String password;

  @OneToMany(mappedBy = "offeringUser")
  private Set<ViewingOffer> addedOffers;

  @OneToMany(mappedBy = "requestingUser")
  private Set<ViewingRequest> addedRequests;

  @NotNull
  @ManyToMany(fetch = FetchType.EAGER)
  private Set<Attribute> attributes;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Role role;

  @NotBlank
  private String cityOfOrigin;

  @NotBlank
  @Column(unique = true)
  @Email
  private String email;

  @CreationTimestamp
  private LocalDateTime registeredSince;

  @UpdateTimestamp
  private LocalDateTime lastModified;

  public MaklerUser() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
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

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Set<ViewingOffer> getAddedOffers() {
    return addedOffers;
  }

  public void setAddedOffers(Set<ViewingOffer> addedOffers) {
    this.addedOffers = addedOffers;
  }

  public Set<ViewingRequest> getAddedRequests() {
    return addedRequests;
  }

  public void setAddedRequests(Set<ViewingRequest> addedRequests) {
    this.addedRequests = addedRequests;
  }

  public void addAddedOffer(ViewingOffer addedOffer) {
    if (addedOffers == null) {
      addedOffers = new HashSet<>();
    }
    this.addedOffers.add(addedOffer);
  }

  public void addAddedRequest(ViewingRequest addedRequest) {
    if (addedRequests == null) {
      addedRequests = new HashSet<>();
    }
    this.addedRequests.add(addedRequest);
  }

  public Set<Attribute> getAttributes() {
    return attributes;
  }

  public void setAttributes(Set<Attribute> attributes) {
    this.attributes = attributes;
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

  public LocalDateTime getRegisteredSince() {
    return registeredSince;
  }

  public void setRegisteredSince(LocalDateTime registeredSince) {
    this.registeredSince = registeredSince;
  }

  public LocalDateTime getLastModified() {
    return lastModified;
  }

  public void setLastModified(LocalDateTime lastModified) {
    this.lastModified = lastModified;
  }
}
