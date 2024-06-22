package br.com.ewerton.servicedoctor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(@Email @NotBlank String email, @NotBlank String password) {

    @Override
    public @Email @NotBlank String email() {
        return email;
    }

    @Override
    public @NotBlank String password() {
        return password;
    }
}
