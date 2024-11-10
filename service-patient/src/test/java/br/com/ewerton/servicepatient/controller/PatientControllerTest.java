package br.com.ewerton.servicepatient.controller;

import br.com.ewerton.servicepatient.dto.PatientDTO;
import br.com.ewerton.servicepatient.model.PatientModel;
import br.com.ewerton.servicepatient.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    private MockMvc mockMvc;

    private PatientModel patient;

    @BeforeEach
    void setUp() {
        // Inicializando o MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();

        // Criando um paciente fictício
        patient = new PatientModel();
        patient.setId(UUID.randomUUID());
        patient.setName("John Doe");
        patient.setPhone("1234567890");
        patient.setAddress("123 Main St");
        patient.setEmail("johndoe@example.com");
    }

    @Test
    void testGetAllPatients() throws Exception {
        // Configura o comportamento do serviço mockado
        when(patientService.allPatient()).thenReturn(Arrays.asList(patient));

        // Realiza uma requisição GET para o endpoint "/patient-service"
        mockMvc.perform(get("/patient-service"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].phone").value("1234567890"))
                .andExpect(jsonPath("$[0].address").value("123 Main St"))
                .andExpect(jsonPath("$[0].email").value("johndoe@example.com"));

        // Verifica se o método do serviço foi chamado
        verify(patientService, times(1)).allPatient();
    }

    @Test
    void testFindPatientById() throws Exception {
        // Configura o comportamento do serviço mockado
        when(patientService.findPatientById(patient.getId())).thenReturn(Optional.of(patient));

        // Realiza uma requisição GET para o endpoint "/patient-service/{id}"
        mockMvc.perform(get("/patient-service/{id}", patient.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.phone").value("1234567890"))
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"));

        // Verifica se o método do serviço foi chamado
        verify(patientService, times(1)).findPatientById(patient.getId());
    }

    @Test
    void testFindPatientByIdNotFound() throws Exception {
        // Configura o comportamento do serviço mockado para retornar Optional.empty()
        when(patientService.findPatientById(any(UUID.class))).thenReturn(Optional.empty());

        // Realiza uma requisição GET para o endpoint "/patient-service/{id}"
        mockMvc.perform(get("/patient-service/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());

        // Verifica se o método do serviço foi chamado
        verify(patientService, times(1)).findPatientById(any(UUID.class));
    }

    @Test
    void testDeletePatient() throws Exception {
        // Configura o comportamento do serviço mockado
        when(patientService.findPatientById(patient.getId())).thenReturn(Optional.of(patient));

        // Realiza uma requisição DELETE para o endpoint "/patient-service/{id}"
        mockMvc.perform(delete("/patient-service/{id}", patient.getId()))
                .andExpect(status().isNoContent());

        // Verifica se o método do serviço foi chamado
        verify(patientService, times(1)).deletePatient(patient.getId());
    }

    @Test
    void testDeletePatientNotFound() throws Exception {
        // Configura o comportamento do serviço mockado para retornar Optional.empty()
        when(patientService.findPatientById(any(UUID.class))).thenReturn(Optional.empty());

        // Realiza uma requisição DELETE para o endpoint "/patient-service/{id}"
        mockMvc.perform(delete("/patient-service/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());

        // Verifica se o método do serviço foi chamado
        verify(patientService, times(1)).findPatientById(any(UUID.class));
    }
}

