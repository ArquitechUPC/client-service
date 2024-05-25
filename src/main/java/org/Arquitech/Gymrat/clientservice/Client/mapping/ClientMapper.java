package org.Arquitech.Gymrat.clientservice.Client.mapping;

import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Client;
import org.Arquitech.Gymrat.clientservice.Client.resource.client.*;
import org.Arquitech.Gymrat.clientservice.Shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class ClientMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;
    public Client toModel(CreateClientResource resource){ return this.mapper.map(resource, Client.class);}
    public Client toModel(UpdateClientResource resource){ return this.mapper.map(resource, Client.class);}
    public Client toModel(CreateClientUserResource resource){return this.mapper.map(resource, Client.class);}
    public Client toModel(ClientPlanResource resource){return this.mapper.map(resource, Client.class);}
    public ClientResource toResource(Client client){ return this.mapper.map(client, ClientResource.class);}

}
