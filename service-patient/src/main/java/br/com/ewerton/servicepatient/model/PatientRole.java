package br.com.ewerton.servicepatient.model;

public enum PatientRole {

    ADMIN("admin"),
    USER("user");

    private String role;

    PatientRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
