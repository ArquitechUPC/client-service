package org.Arquitech.Gymrat.clientservice.Client.mapping;

import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Client;
import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Measurement;
import org.Arquitech.Gymrat.clientservice.Client.resource.client.ClientResource;
import org.Arquitech.Gymrat.clientservice.Client.resource.client.CreateClientResource;
import org.Arquitech.Gymrat.clientservice.Client.resource.client.UpdateClientResource;
import org.Arquitech.Gymrat.clientservice.Client.resource.measurement.MeasurementResource;
import org.Arquitech.Gymrat.clientservice.Shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class ClientMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;
    public Client toModel(CreateClientResource resource){ return this.mapper.map(resource, Client.class);}
    public Client toModel(UpdateClientResource resource){ return this.mapper.map(resource, Client.class);}
    public ClientResource toResource(Client client){ return this.mapper.map(client, ClientResource.class);}
    public Measurement toModel(MeasurementResource measurementResource) {
        return this.mapper.map(measurementResource, Measurement.class);
    }
}
