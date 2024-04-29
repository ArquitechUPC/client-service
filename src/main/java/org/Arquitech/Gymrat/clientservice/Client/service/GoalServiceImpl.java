package org.Arquitech.Gymrat.clientservice.Client.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Client;
import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Goal;
import org.Arquitech.Gymrat.clientservice.Client.domain.persistence.ClientRepository;
import org.Arquitech.Gymrat.clientservice.Client.domain.persistence.GoalRepository;
import org.Arquitech.Gymrat.clientservice.Client.domain.persistence.MeasurementRepository;
import org.Arquitech.Gymrat.clientservice.Client.domain.service.GoalService;
import org.Arquitech.Gymrat.clientservice.Shared.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private Validator validator;

    public GoalServiceImpl(GoalRepository goalRepository, MeasurementRepository measurementRepository, ClientRepository clientRepository, Validator validator){
        this.goalRepository = goalRepository;
        this.measurementRepository = measurementRepository;
        this.clientRepository = clientRepository;
        this.validator = validator;
    }



    @Transactional(readOnly = true)
    @Override
    public List<Goal> fetchAll() {
        return goalRepository.findAll();
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<Goal> fetchById(Integer Id) {
        if (goalRepository.existsById(Id)) {
        return goalRepository.findById(Id);
        } else {
            throw new CustomException("Goal not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Goal save(Goal goal, Integer givenClientId) {
        Set<ConstraintViolation<Goal>> violations = validator.validate(goal);
        if (!violations.isEmpty()) {
            throw new CustomException("Error", HttpStatus.NOT_FOUND);
        }

        Client client = clientRepository.findById(givenClientId)
                .orElseThrow(()-> new CustomException("Client not found", HttpStatus.NOT_FOUND));
         client.getGoals().add(goal);
         clientRepository.save(client);
         return goalRepository.save(goal);
    }

    @Override
    public Goal update(Goal goal) {
        Set<ConstraintViolation<Goal>> violations = validator.validate(goal);
        if (!violations.isEmpty()) {
            throw new CustomException("Error", HttpStatus.NOT_FOUND);
        }

        return goalRepository.findById(goal.getId())
                .map(goalToUpdate ->{
                    goalToUpdate.setName(goal.getName());
                    goalToUpdate.setDescription(goal.getDescription());
                    goalToUpdate.setEndDate(goal.getEndDate());

                    return goalRepository.save(goalToUpdate);
                })
                .orElseThrow(()-> new CustomException("Goal not found", HttpStatus.NOT_FOUND));

    }

    @Override
    public boolean deleteById(Integer id){
        var goalToDelete = goalRepository.findById(id)
                .orElseThrow(() -> new CustomException("Goal not found", HttpStatus.NOT_FOUND));

        goalRepository.delete(goalToDelete);
        return true;
    }
}
