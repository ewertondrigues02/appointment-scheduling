package br.com.ewerton.servicepatient.dto;

import br.com.ewerton.servicepatient.model.PatientRole;

public record RegisterDTO(String email, String password, PatientRole role) {

    @Override
    public String email() {
        return email;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public PatientRole role() {
        return role;
    }
}
