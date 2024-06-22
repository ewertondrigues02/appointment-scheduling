package br.com.ewerton.servicedoctor.model;

import jakarta.validation.constraints.NotBlank;

public enum DoctorRole {

    ADMIN("admin"),
    USER("user");

    @NotBlank
    private final String role;

    DoctorRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
