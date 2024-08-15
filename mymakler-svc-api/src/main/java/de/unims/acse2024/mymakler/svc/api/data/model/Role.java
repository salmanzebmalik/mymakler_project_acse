package de.unims.acse2024.mymakler.svc.api.data.model;

public enum Role {
  LANDLORD(-100),
  TENANT(100);

  private final int val;

  Role(int val) {
    this.val = val;
  }

  public static Role of(String s) {
    if ("Landlord".equalsIgnoreCase(s)) {
      return LANDLORD;
    } else if ("Tenant".equalsIgnoreCase(s)) {
      return TENANT;
    } else {
      throw new IllegalArgumentException("Unknown user role: " + s);
    }
  }

  public int getVal() {
    return val;
  }
}
