package br.com.ewerton.servicepatient.dto;

public record EmailLoginDTO(String email, String password) {
    @Override
    public String email() {
        return email;
    }

    @Override
    public String password() {
        return password;
    }

    public EmailLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
