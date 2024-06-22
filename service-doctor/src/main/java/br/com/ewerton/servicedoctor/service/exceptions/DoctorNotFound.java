package br.com.ewerton.servicedoctor.service.exceptions;

public class DoctorNotFound extends RuntimeException{

    public DoctorNotFound(String msg){
        super("Doctor not found");
    }
}
