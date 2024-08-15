package de.unims.acse2024.mymakler.svc.api.data.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Pattern;

@Entity
public class Attribute implements Serializable {
  private static final long serialVersionUID = -4497533357368327179L;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "attributes")
  private Set<MaklerUser> users;

  @Id
  @Pattern(regexp = "[a-zA-Z0-9]+")
  private String name;

  public Attribute() {}

  public Attribute(String name) {
    this.name = name;
  }

  public static Set<Attribute> of(String[] attributes) {
    return Arrays.stream(attributes).map(Attribute::new).collect(Collectors.toSet());
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<MaklerUser> getUsers() {
    return users;
  }

  public void setUsers(Set<MaklerUser> users) {
    this.users = users;
  }

  public void addUser(MaklerUser user) {
    if (users == null) {
      users = new HashSet<>();
    }
    users.add(user);
  }
}
