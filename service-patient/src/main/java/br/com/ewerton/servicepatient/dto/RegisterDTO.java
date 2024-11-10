package br.com.ewerton.servicepatient.dto;

public record RegisterDTO(String email, String password, String role) {

    @Override
    public String email() {
        return email;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public String role() {
        return role;
    }
}
