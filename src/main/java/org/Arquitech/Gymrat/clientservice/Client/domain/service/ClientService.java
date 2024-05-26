package org.Arquitech.Gymrat.clientservice.Client.domain.service;

import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client>fetchAll();
    Optional<Client> fetchById(Integer Id);
    Client save(Client client);
    Client updatePlan(Client client, Integer planId);
    Optional<List<Client>> updateAllClassExits(Integer newClassExits);
    Optional<?> updateClassStatus(Client client, Integer classId);
    List<Optional<?>> fetchClientClass(Integer id);
    Optional<?> joinClass(Integer id, Integer classId);
    Optional<?> exitClass(Integer id, Integer classId);
    boolean deleteById(Integer id);
    boolean existUserByUserId(Client client);
    Integer obtainUserId(String username, String email, String password, String phoneNumber, String address, String city, Integer companyId);
    boolean existPlanByPlanId(Integer id);
}
