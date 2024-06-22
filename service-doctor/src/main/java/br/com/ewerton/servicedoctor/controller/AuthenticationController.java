package br.com.ewerton.servicedoctor.controller;


import br.com.ewerton.servicedoctor.configuration.security.TokenService;
import br.com.ewerton.servicedoctor.dto.AuthenticationDTO;
import br.com.ewerton.servicedoctor.dto.EmailResponseDTO;
import br.com.ewerton.servicedoctor.dto.RegisterDTO;
import br.com.ewerton.servicedoctor.model.DoctorModel;
import br.com.ewerton.servicedoctor.repository.DoctorRepository;
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

@RestController
@RequestMapping( "auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping( "/login")
    public ResponseEntity<EmailResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authentication = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((DoctorModel) authentication.getPrincipal());
        return ResponseEntity.ok(new EmailResponseDTO(token));
    }


    @PostMapping("/register")
    public ResponseEntity<DoctorModel> register(@RequestBody @Valid RegisterDTO data) {
        if (this.doctorRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        DoctorModel newDoctor = new DoctorModel(data.email(), encryptedPassword, data.role());
        this.doctorRepository.save(newDoctor);
        return ResponseEntity.ok().build();
    }
}
