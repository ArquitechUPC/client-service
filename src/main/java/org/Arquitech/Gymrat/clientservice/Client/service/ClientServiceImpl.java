package org.Arquitech.Gymrat.clientservice.Client.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.Arquitech.Gymrat.clientservice.Client.client.ClassClient;
import org.Arquitech.Gymrat.clientservice.Client.client.PlanClient;
import org.Arquitech.Gymrat.clientservice.Client.client.UserClient;
import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Client;
import org.Arquitech.Gymrat.clientservice.Client.domain.persistence.ClientRepository;
import org.Arquitech.Gymrat.clientservice.Client.domain.persistence.GoalRepository;
import org.Arquitech.Gymrat.clientservice.Client.domain.persistence.MeasurementRepository;
import org.Arquitech.Gymrat.clientservice.Client.domain.service.ClientService;
import org.Arquitech.Gymrat.clientservice.Client.resource.client.CreateClientUserResource;
import org.Arquitech.Gymrat.clientservice.Client.resource.client.RequestUserCompany;
import org.Arquitech.Gymrat.clientservice.Shared.exception.CustomException;


//import org.Arquitech.Gymrat.Authentication.domain.model.entity.User;
//import org.Arquitech.Gymrat.Authentication.domain.persistence.UserRepository;
//import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Plan;
//import org.Arquitech.Gymrat.clientservice.Client.domain.persistence.PlanRepository;
import org.Arquitech.Gymrat.clientservice.Client.domain.persistence.ClientRepository;
import org.Arquitech.Gymrat.clientservice.Client.domain.persistence.GoalRepository;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private PlanClient planClient;

    @Autowired
    private ClassClient classClient;

    @Autowired
    private GoalRepository goalRepository;


    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private Validator validator;

    public ClientServiceImpl(ClientRepository clientRepository, UserClient userClient, PlanClient planClient,ClassClient classClient,/*UserRepository userRepository,*/ GoalRepository goalRepository, MeasurementRepository measurementRepository, Validator validator) {
        this.clientRepository = clientRepository;
        this.userClient = userClient;
        this.planClient = planClient;
        this.classClient = classClient;
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
            throw new CustomException("El cliente no existe",HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public Client save(Client client) {
        /*Set<ConstraintViolation<Client>> violations = validator.validate(client);
        if (!violations.isEmpty()) {
            throw new CustomException("Error",HttpStatus.NOT_FOUND);
        }*/
        client.setClassExits(2);  //aqui debe consumir del microservicio del admin un valor de la tabla admin

       // client.setCompanyId(1); //Aquí debe consultar el microservicio de administración para obtener el valor de la tabla 'admin' que retorne el ID de la compañía asociada al administrador

        return clientRepository.save(client);
    }

    @Override
    public Client updatePlan(Client client, Integer planId) {
        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        if (!violations.isEmpty()) {
            throw new CustomException("Error", HttpStatus.NOT_FOUND);
        }
            client.setGivenPlan(planId);
            return clientRepository.save(client);

    }

    @Override
    public Optional<List<Client>> updateAllClassExits(Integer newClassExits) {

        List<Client> aux = clientRepository.findAll();

        aux.forEach(
                client -> {
                    client.setClassExits(newClassExits);
                    clientRepository.save(client);
                }
        );
        return Optional.of(aux);
    }

    @Override
    public Optional<?> updateClassStatus(Client client, Integer classId) {

        if(!clientRepository.verifyClientClassStatus(client.getId(), classId)){
            clientRepository.updateClientClass(client.getId(), classId, true);
        } else {
            throw new CustomException("this class has already been attended", HttpStatus.BAD_REQUEST);
        }

        throw new CustomException("Successfully status updated", HttpStatus.OK);
    }


    @Override
    public List<Optional<?>> fetchClientClass(Integer id) {

        Client client =  clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));

        Set<Integer> classIds  = client.getClassIds();
        return classIds.stream()
                .map(classClient::fetchClassById).collect(Collectors.toList());

        //classClient.fetchClientClass(id);
    }

    @Override
    public boolean deleteById(Integer id) {
        var clientToDelete = clientRepository.findById(id).orElseThrow(() -> new CustomException("Error",HttpStatus.NOT_FOUND));

        clientRepository.delete(clientToDelete);
        return true;
    }

    @Override
    public boolean existUserByUserId(Client client) {
        return userClient.existUserById(client.getGivenUser());
    }

    @Override
    public Integer obtainUserId(String username, String email, String password, String phoneNumber, String address, String city, Integer companyId) {
        RequestUserCompany request = new RequestUserCompany();
        request.setUsername(username);
        request.setEmail(email);
        request.setPassword(password);
        request.setPhoneNumber(phoneNumber);
        request.setAddress(address);
        request.setCity(city);
        request.setCompanyId(companyId);


        return userClient.ObtainUserId(request);
    }

    @Override
    public boolean existPlanByPlanId(Integer id) {
        return (planClient.existPlanById(id)).isPresent();
    }


    @Override
    public Optional<?> joinClass(Integer id, Integer classId){
    Client aux = clientRepository.findById(id).get();
        if(aux.getClassIds().contains(classId)){
            throw new CustomException("Client already joined the class", HttpStatus.BAD_REQUEST);
        }
        else {
            if(classClient.availabilityClass(classId)){

                clientRepository.insertClientClass(id, classId, false);

                clientRepository.save(aux);

                classClient.updateVacancy(classId);

            }
            else {
                throw new CustomException("Class without vacancies", HttpStatus.BAD_REQUEST);
            }
        }
        throw new CustomException("Successful Class registration", HttpStatus.ACCEPTED);
    }

    @Override
    public Optional<?> exitClass(Integer id, Integer classId) {
        Client aux = clientRepository.findById(id).get();
        if(aux.getClassIds().contains(classId)){

            if(clientRepository.findById(id).get().getClassExits()>0){
                aux.getClassIds().removeIf(classIdInList -> classIdInList.equals(classId));
                aux.setClassExits(aux.getClassExits().intValue()-1);
                clientRepository.save(aux);
                classClient.increasesVacancy(classId);
            }
            else {
                throw new CustomException("You have no more class trips available. Try again next week.", HttpStatus.NOT_FOUND);
            }

        }
        else {
            throw new CustomException("The class you entered was not found", HttpStatus.NOT_FOUND);
        }
        throw new CustomException("Successful Class exit", HttpStatus.ACCEPTED);
    }


}
