package br.com.ewerton.servicepatient.dto;

public record EmailResponseDTO(String token) {

    @Override
    public String token() {
        return token;
    }
}
