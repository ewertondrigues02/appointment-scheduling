package br.com.ewerton.servicepatient.controller;

import br.com.ewerton.servicepatient.dto.PatientDTO;
import br.com.ewerton.servicepatient.model.PatientModel;
import br.com.ewerton.servicepatient.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Tag(name = "Patients endpoints")
@RestController
@RequestMapping("/patient-service")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Operation(summary = "Find a List of registered patients")
    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        List<PatientModel> patients = patientService.allPatient();
        List<PatientDTO> patientDTOS = patients.stream()
                .map(patient -> new PatientDTO(patient.getName(), patient.getPhone(), patient.getAddress(), patient.getEmail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(patientDTOS);
    }

    @Operation(summary = "Find a specific patient by your ID")
    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> findPatientById(@PathVariable  UUID id) {
        Optional<PatientModel> patient = patientService.findPatientById(id);
        if (patient.isPresent()) {
            PatientDTO patientDTO = new PatientDTO(patient.get().getName(), patient.get().getPhone(), patient.get().getAddress(), patient.get().getEmail());
            return ResponseEntity.ok().body(patientDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a  specific patient by your ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable  UUID id) {
        Optional<PatientModel> patient = patientService.findPatientById(id);
        if (patient.isPresent()) {
            patientService.deletePatient(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
