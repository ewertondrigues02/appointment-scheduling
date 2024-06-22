package br.com.ewerton.servicedoctor.dto;

import br.com.ewerton.servicedoctor.model.DoctorRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(@Email @NotBlank String email , @NotBlank String password, DoctorRole role) {
}
