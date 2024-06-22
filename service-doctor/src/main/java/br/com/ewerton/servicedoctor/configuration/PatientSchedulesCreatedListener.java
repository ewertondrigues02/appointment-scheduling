package br.com.ewerton.servicedoctor.configuration;

import br.com.ewerton.servicedoctor.dto.PatientDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PatientSchedulesCreatedListener {

    @RabbitListener(queues = "schedules.v1.patients-schedules-created-queue-doctor")
    public void onPatientSchedulesCreated(PatientDTO patientDTO) {
        System.out.println("Patient Scheduled: " + patientDTO);
    }
}
