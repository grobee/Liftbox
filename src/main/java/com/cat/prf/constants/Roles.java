package com.cat.prf.constants;

public enum Roles {
    USER("USER"),
    ADMIN("ADMIN");

    String role;

    Roles(String role) {
        this.role = role;
    }

    public String getName() {
        return role;
    }
}
