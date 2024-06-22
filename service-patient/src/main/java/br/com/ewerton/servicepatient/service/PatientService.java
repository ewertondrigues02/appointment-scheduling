package br.com.ewerton.servicepatient.service;

import br.com.ewerton.servicepatient.model.PatientModel;
import br.com.ewerton.servicepatient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<PatientModel> allPatient() {
        return patientRepository.findAll();
    }

    public Optional<PatientModel> findPatientById(UUID id) {
        return patientRepository.findById(id);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }

    public PatientModel savePatient(PatientModel obj) {
        return patientRepository.save(obj);
    }
}
