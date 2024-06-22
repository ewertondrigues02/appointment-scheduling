package br.com.ewerton.servicedoctor.dto;

import jakarta.validation.Valid;

public record PatientDTO(String name, String phone, String address, @Valid String email) {

    public PatientDTO(String name, String phone, String address, String email) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String phone() {
        return phone;
    }

    @Override
    public String address() {
        return address;
    }

    @Override
    public @Valid String email() {
        return email;
    }
}
