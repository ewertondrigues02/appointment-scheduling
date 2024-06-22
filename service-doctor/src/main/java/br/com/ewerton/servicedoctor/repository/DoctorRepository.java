package br.com.ewerton.servicedoctor.repository;

import br.com.ewerton.servicedoctor.model.DoctorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorModel, UUID> {

    UserDetails findByEmail(String email);
}
