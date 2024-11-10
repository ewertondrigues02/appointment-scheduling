package br.com.ewerton.servicepatient.controller;

import br.com.ewerton.servicepatient.controller.security.TokenService;
import br.com.ewerton.servicepatient.dto.AuthenticationDTO;
import br.com.ewerton.servicepatient.dto.EmailResponseDTO;
import br.com.ewerton.servicepatient.dto.RegisterDTO;
import br.com.ewerton.servicepatient.model.PatientModel;
import br.com.ewerton.servicepatient.repository.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("patient-service/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authentication = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((PatientModel) authentication.getPrincipal());
        return ResponseEntity.ok(new EmailResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<List<PatientModel>> registerPatient(@RequestBody @Valid RegisterDTO data) {
        if (this.patientRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        PatientModel newPatient = new PatientModel(data.email(), encryptedPassword, data.role());
        this.patientRepository.save(newPatient);
        return ResponseEntity.ok().build();
    }
}
