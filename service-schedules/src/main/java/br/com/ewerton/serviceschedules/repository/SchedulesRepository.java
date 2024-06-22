package br.com.ewerton.serviceschedules.repository;

import br.com.ewerton.serviceschedules.dto.PatientDTO;
import br.com.ewerton.serviceschedules.model.SchedulesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SchedulesRepository extends JpaRepository<SchedulesModel, UUID> {
}
