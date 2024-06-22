package br.com.ewerton.servicedoctor.service;

import br.com.ewerton.servicedoctor.model.DoctorModel;
import br.com.ewerton.servicedoctor.repository.DoctorRepository;
import br.com.ewerton.servicedoctor.service.exceptions.DoctorNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public List<DoctorModel> allDoctor() {
        if (doctorRepository.count() != 0) {
            return doctorRepository.findAll();
        } else {
            throw new DoctorNotFound("Doctor not found");
        }
    }

    public Optional<DoctorModel> findDoctorById(UUID id) {
        if (doctorRepository.count() != 0) {
            Optional<DoctorModel> doctor = doctorRepository.findById(id);
        }
        if (id != null && allDoctor().isEmpty()) {
            return doctorRepository.findById(id);
        } else {
            throw new DoctorNotFound("Doctor not found");
        }
    }

}
