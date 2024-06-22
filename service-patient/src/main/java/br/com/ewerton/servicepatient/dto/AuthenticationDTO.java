package br.com.ewerton.servicepatient.dto;

public record AuthenticationDTO(String email, String password) {

    @Override
    public String email() {
        return email;
    }

    @Override
    public String password() {
        return password;
    }
}
