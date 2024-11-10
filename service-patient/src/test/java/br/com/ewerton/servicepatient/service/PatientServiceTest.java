package br.com.ewerton.servicepatient.service;

import br.com.ewerton.servicepatient.model.PatientModel;
import br.com.ewerton.servicepatient.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private PatientModel patient;

    @BeforeEach
    void setUp() {
        // Configura o paciente fictício para os testes
        patient = new PatientModel();
        patient.setId(UUID.randomUUID());
        patient.setName("John Doe");

    }

    @Test
    void testAllPatient() {
        // Dados de teste
        List<PatientModel> patientList = Arrays.asList(patient);

        // Configura o comportamento do mock
        when(patientRepository.findAll()).thenReturn(patientList);

        // Chama o método a ser testado
        List<PatientModel> result = patientService.allPatient();

        // Verifica se o resultado é o esperado
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());

        // Verifica se o método do repositório foi chamado
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void testFindPatientById() {
        // Configura o comportamento do mock
        when(patientRepository.findById(any(UUID.class))).thenReturn(Optional.of(patient));

        // Chama o método a ser testado
        Optional<PatientModel> result = patientService.findPatientById(patient.getId());

        // Verifica se o resultado é o esperado
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());

        // Verifica se o método do repositório foi chamado
        verify(patientRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void testFindPatientByIdNotFound() {
        // Configura o comportamento do mock para não encontrar o paciente
        when(patientRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Chama o método a ser testado
        Optional<PatientModel> result = patientService.findPatientById(UUID.randomUUID());

        // Verifica se o resultado é o esperado
        assertFalse(result.isPresent());

        // Verifica se o método do repositório foi chamado
        verify(patientRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void testSavePatient() {
        // Configura o comportamento do mock
        when(patientRepository.save(any(PatientModel.class))).thenReturn(patient);

        // Chama o método a ser testado
        PatientModel result = patientService.savePatient(patient);

        // Verifica se o resultado é o esperado
        assertNotNull(result);
        assertEquals("John Doe", result.getName());

        // Verifica se o método do repositório foi chamado
        verify(patientRepository, times(1)).save(any(PatientModel.class));
    }

    @Test
    void testDeletePatient() {
        // Chama o método a ser testado
        patientService.deletePatient(patient.getId());

        // Verifica se o método do repositório foi chamado
        verify(patientRepository, times(1)).deleteById(any(UUID.class));
    }
}

