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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PatientsSchedulesControllerTest {

    @Mock
    private PatientService patientService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private PatientsSchedulesController patientsSchedulesController;

    private MockMvc mockMvc;
    private PatientDTO patientDTO;
    private PatientModel patientModel;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(patientsSchedulesController).build();

        // Criando um PatientDTO para os testes
        patientDTO = new PatientDTO("John Doe", "123 Main St", "1234567890", "johndoe@example.com");

        // Criando o PatientModel correspondente
        patientModel = new PatientModel(patientDTO.name(), patientDTO.address(), patientDTO.phone(), patientDTO.email());
    }

    @Test
    void testPatientsSchedules() throws Exception {
        // Configura o comportamento do serviço mockado
        when(patientService.savePatient(any(PatientModel.class))).thenReturn(patientModel);

        // Simula a conversão e o envio para o RabbitMQ
        doNothing().when(rabbitTemplate).convertAndSend(eq("schedules.v1.patients-schedules-created"), eq(""), any(PatientDTO.class));

        // Realiza uma requisição POST para o endpoint "/patient-service/schedules"
        mockMvc.perform(post("/patient-service/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(patientDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.phone").value("1234567890"))
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"));

        // Verifica se o método do serviço foi chamado para salvar o paciente
        verify(patientService, times(1)).savePatient(any(PatientModel.class));

        // Verifica se o RabbitTemplate foi chamado para enviar a mensagem
        verify(rabbitTemplate, times(1)).convertAndSend(eq("schedules.v1.patients-schedules-created"), eq(""), any(PatientDTO.class));
    }

    @Test
    void testPatientsSchedules_WhenSaveFails() throws Exception {
        // Configura o comportamento do serviço mockado para lançar uma exceção
        when(patientService.savePatient(any(PatientModel.class))).thenThrow(new RuntimeException("Failed to save patient"));

        // Realiza uma requisição POST para o endpoint "/patient-service/schedules"
        mockMvc.perform(post("/patient-service/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(patientDTO)))
                .andExpect(status().isInternalServerError());

        // Verifica se o método do serviço foi chamado para salvar o paciente
        verify(patientService, times(1)).savePatient(any(PatientModel.class));

        // Verifica se o RabbitTemplate não foi chamado (já que o erro ocorreu antes)
        verify(rabbitTemplate, times(0)).convertAndSend(anyString(), anyString(), any(PatientDTO.class));
    }

    // Método auxiliar para converter objetos para JSON
    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

