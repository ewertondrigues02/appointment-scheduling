package br.com.ewerton.serviceschedules.configuration;

import br.com.ewerton.serviceschedules.dto.PatientDTO;
import br.com.ewerton.serviceschedules.model.SchedulesModel;
import br.com.ewerton.serviceschedules.repository.SchedulesRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientSchedulesCreatedListener {

    @Autowired
    private SchedulesRepository schedulesRepository;

    @RabbitListener(queues = "schedules.v1.patients-schedules-created-queue-schedules")
    public void onPatientSchedulesCreated(PatientDTO patientDTO) {
        SchedulesModel schedulesModel = new SchedulesModel();
        schedulesModel.setName(patientDTO.name());
        schedulesModel.setPhone(patientDTO.phone());
        schedulesModel.setAddress(patientDTO.address());
        schedulesModel.setEmail(patientDTO.email());

        schedulesRepository.save(schedulesModel);

        System.out.println("Patient Scheduled: " + patientDTO);
    }
}
