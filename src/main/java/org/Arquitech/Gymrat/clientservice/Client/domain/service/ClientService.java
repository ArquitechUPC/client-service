package org.Arquitech.Gymrat.clientservice.Client.domain.service;

import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Client;
import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Goal;
import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Measurement;
import org.Arquitech.Gymrat.clientservice.Client.resource.invoice.InvoiceResource;
import org.Arquitech.Gymrat.clientservice.Client.resource.measurement.MeasurementResource;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client>fetchAll();
    Optional<Client> fetchById(Integer Id);
    Client save(Client client);
    Client updatePlan(Client client, Integer planId, boolean existPlan);
    boolean deleteById(Integer id);
    Client update(Client client);
    boolean existUserByUserId(Client client);
    boolean existPlanByPlanId(Integer id);
    List<InvoiceResource> getInvoicesByClientId(Integer clientId);
    void sendProgressNotification(Integer userId, Goal goal, Measurement latestMeasurement);
    Measurement saveMeasurement(MeasurementResource measurementResource);
}
