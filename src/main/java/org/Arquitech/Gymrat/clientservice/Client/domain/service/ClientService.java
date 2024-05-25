package org.Arquitech.Gymrat.clientservice.Client.domain.service;

import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client>fetchAll();
    Optional<Client> fetchById(Integer Id);
    Client save(Client client);
    Client updatePlan(Client client, Integer planId, boolean existPlan);
    boolean deleteById(Integer id);
    boolean existUserByUserId(Client client);
    boolean existPlanByPlanId(Integer id);
}
