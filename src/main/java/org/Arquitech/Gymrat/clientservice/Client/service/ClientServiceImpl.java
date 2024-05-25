package org.Arquitech.Gymrat.clientservice.Client.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.Arquitech.Gymrat.clientservice.Client.client.PlanClient;
import org.Arquitech.Gymrat.clientservice.Client.client.UserClient;
import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Client;
import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Goal;
import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Measurement;
import org.Arquitech.Gymrat.clientservice.Client.domain.persistence.ClientRepository;
import org.Arquitech.Gymrat.clientservice.Client.domain.persistence.GoalRepository;
import org.Arquitech.Gymrat.clientservice.Client.domain.persistence.MeasurementRepository;
import org.Arquitech.Gymrat.clientservice.Client.domain.service.ClientService;
import org.Arquitech.Gymrat.clientservice.Client.mapping.ClientMapper;
import org.Arquitech.Gymrat.clientservice.Client.resource.admin.AdminServiceClient;
import org.Arquitech.Gymrat.clientservice.Client.resource.invoice.InvoiceResource;
import org.Arquitech.Gymrat.clientservice.Client.resource.measurement.MeasurementResource;
import org.Arquitech.Gymrat.clientservice.Shared.exception.CustomException;


//import org.Arquitech.Gymrat.Authentication.domain.model.entity.User;
//import org.Arquitech.Gymrat.Authentication.domain.persistence.UserRepository;
//import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Plan;
//import org.Arquitech.Gymrat.clientservice.Client.domain.persistence.PlanRepository;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper mapper;

    //@Autowired
    //private UserRepository userRepository;
    @Autowired
    private UserClient userClient;

    @Autowired
    private PlanClient planClient;

    @Autowired
    private GoalRepository goalRepository;

    //@Autowired
    //private PlanRepository planRepository;

    @Autowired
    private AdminServiceClient adminServiceClient;

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private Validator validator;

    public ClientServiceImpl(ClientRepository clientRepository, UserClient userClient, PlanClient planClient,/*UserRepository userRepository,*/ GoalRepository goalRepository, MeasurementRepository measurementRepository, Validator validator) {
        this.clientRepository = clientRepository;
        this.userClient = userClient;
        this.planClient = planClient;
        //this.userRepository = userRepository;
        this.goalRepository = goalRepository;
        this.measurementRepository = measurementRepository;
        this.validator = validator;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Client> fetchAll() {
        return clientRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Client> fetchById(Integer Id) {
        if (clientRepository.existsById(Id)) {
            return clientRepository.findById(Id);
        } else {
            throw new CustomException("El cliente no existe", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Client save(Client client) {
        /*Set<ConstraintViolation<Client>> violations = validator.validate(client);
        if (!violations.isEmpty()) {
            throw new CustomException("Error",HttpStatus.NOT_FOUND);
        }*/

        return clientRepository.save(client);
    }

    @Override
    public Client updatePlan(Client client, Integer planId, boolean existPlan) {
        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        if (!violations.isEmpty()) {
            throw new CustomException("Error", HttpStatus.NOT_FOUND);
        }
/*
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException("Plan not found", HttpStatus.NOT_FOUND));
*/
        if (existPlan) {
            client.setGivenPlan(planId);
            return clientRepository.save(client);
        } else {
            throw new CustomException("Plan not found", HttpStatus.NOT_FOUND);
        }


    }

    @Override
    public boolean deleteById(Integer id) {
        var clientToDelete = clientRepository.findById(id).orElseThrow(() -> new CustomException("Error", HttpStatus.NOT_FOUND));

        clientRepository.delete(clientToDelete);
        return true;
    }

    @Override
    public Client update(Client client) {
        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        if (!violations.isEmpty()) {
            throw new CustomException("Error validating client data", HttpStatus.BAD_REQUEST);
        }
        return clientRepository.save(client);
    }

    @Override
    public boolean existUserByUserId(Client client) {
        return userClient.existUserById(client.getGivenUser());
    }

    @Override
    public boolean existPlanByPlanId(Integer id) {
        return planClient.existPlanById(id);
    }

    @Override
    public List<InvoiceResource> getInvoicesByClientId(Integer clientId) {
        return adminServiceClient.getInvoicesByClientId(clientId);
    }

    @Override
    public Measurement saveMeasurement(MeasurementResource measurementResource) {

        Measurement measurement = mapper.toModel(measurementResource);


        return measurementRepository.save(measurement);
    }


    @Override
    public void sendProgressNotification(Integer userId, Goal goal, Measurement latestMeasurement) {
        if (goal.getNotificationEnabled()) {
            double progressPercentage = (latestMeasurement.getValue() / goal.getTargetValue()) * 100;

            if (progressPercentage >= 50 && goal.getProgressNotificationSent() == false) {
                goal.setProgressNotificationSent(true);
                goalRepository.save(goal);
            }
        }
    }

}
