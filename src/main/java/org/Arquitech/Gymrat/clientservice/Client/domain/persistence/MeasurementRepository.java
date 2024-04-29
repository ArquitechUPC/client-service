package org.Arquitech.Gymrat.clientservice.Client.domain.persistence;

import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    List<Measurement> findByDate(LocalDate date);
}
