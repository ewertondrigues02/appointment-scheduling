package br.com.ewerton.servicepatient.repository;

import br.com.ewerton.servicepatient.model.PatientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<PatientModel, UUID> {

    UserDetails findByEmail(String email);
}
