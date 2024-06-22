package br.com.ewerton.servicepatient.controller;

import br.com.ewerton.servicepatient.dto.PatientDTO;
import br.com.ewerton.servicepatient.model.PatientModel;
import br.com.ewerton.servicepatient.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Schedules endpoint")
@RestController
@RequestMapping("/patient-service")
public class PatientsSchedulesController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Operation(summary = "converts an object of type model to type dto then saves it in the database and sends a message to the queue")
    @PostMapping("/schedules")
    public ResponseEntity<PatientDTO> patientsSchedules(@RequestBody PatientDTO patientDTO) {
        PatientModel patientModel = new PatientModel(patientDTO.name(), patientDTO.address(), patientDTO.phone(), patientDTO.email());
        patientService.savePatient(patientModel);
        PatientDTO savedPatientDTO = new PatientDTO(patientModel.getName(), patientModel.getPhone(), patientModel.getAddress(), patientModel.getEmail());
        rabbitTemplate.convertAndSend("schedules.v1.patients-schedules-created", "", savedPatientDTO);

        return ResponseEntity.ok(savedPatientDTO);

    }
}
