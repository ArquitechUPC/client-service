package org.Arquitech.Gymrat.clientservice.Client.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Client;
import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Measurement;
import org.Arquitech.Gymrat.clientservice.Client.domain.persistence.ClientRepository;
import org.Arquitech.Gymrat.clientservice.Client.domain.persistence.MeasurementRepository;
import org.Arquitech.Gymrat.clientservice.Client.domain.service.MeasurementService;
import org.Arquitech.Gymrat.clientservice.Shared.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private Validator validator;

    public MeasurementServiceImpl(MeasurementRepository measurementRepository, ClientRepository clientRepository, Validator validator) {
        this.measurementRepository = measurementRepository;
        this.clientRepository = clientRepository;
        this.validator = validator;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Measurement> fetchAll() {
        return measurementRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Measurement> fetchById(Integer Id) {
        if (measurementRepository.existsById(Id)) {
            return measurementRepository.findById(Id);
        } else {
            throw new CustomException("No existe", HttpStatus.NOT_FOUND);
        }
    }


    @Transactional(readOnly = true)
    @Override
    public List<Measurement> fetchByClient(Integer Id) {
        return clientRepository.findById(Id)
                .map(Client::getMeasurements)
                .orElseThrow(() -> new CustomException("Client not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public Measurement save(Measurement measurement, Integer givenClientId) {
        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);
        if (!violations.isEmpty()) {
            throw new CustomException("Error", HttpStatus.NOT_FOUND);
        }

        Client client = clientRepository.findById(givenClientId)
                .orElseThrow(() -> new CustomException("Client not found", HttpStatus.NOT_FOUND));
        client.getMeasurements().add(measurement);
        clientRepository.save(client);

        return measurementRepository.save(measurement);
    }

    @Override
    public Measurement update(Measurement measurement) {
        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);
        if (!violations.isEmpty()) {
            throw new CustomException("Error", HttpStatus.NOT_FOUND);
        }

        return measurementRepository
                .findById(measurement.getId())
                .map(updatedMeasurement -> {
                    updatedMeasurement.setWeight(measurement.getWeight());
                    updatedMeasurement.setComment(measurement.getComment());
                    updatedMeasurement.setChestCircumference(measurement.getChestCircumference());
                    updatedMeasurement.setArmCircumference(measurement.getArmCircumference());
                    updatedMeasurement.setHipCircumference(measurement.getHipCircumference());
                    updatedMeasurement.setLegCircumference(measurement.getLegCircumference());
                    updatedMeasurement.setWaistCircumference(measurement.getWaistCircumference());
                    return measurementRepository.save(updatedMeasurement);
                })
                .orElseThrow(() -> new CustomException("Measurement not found", HttpStatus.NOT_FOUND));

    }

    @Override
    public boolean deleteById(Integer id) {
        var measurementToDelete = measurementRepository.findById(id)
                .orElseThrow(() -> new CustomException("Measurement not found", HttpStatus.NOT_FOUND));

        measurementRepository.delete(measurementToDelete);
        return true;
    }
}
