package org.Arquitech.Gymrat.clientservice.Client.domain.service;

import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Measurement;

import java.util.List;
import java.util.Optional;

public interface MeasurementService {
    List<Measurement> fetchAll();
    Optional<Measurement> fetchById(Integer Id);
    List<Measurement> fetchByClient(Integer Id);
    Measurement save(Measurement measurement, Integer givenClientId);
    Measurement update(Measurement measurement);
    boolean deleteById(Integer id);
}
