package org.Arquitech.Gymrat.clientservice.Client.api.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Client;
import org.Arquitech.Gymrat.clientservice.Client.domain.service.ClientService;
import org.Arquitech.Gymrat.clientservice.Client.mapping.ClientMapper;
import org.Arquitech.Gymrat.clientservice.Client.resource.client.*;
import org.Arquitech.Gymrat.clientservice.Shared.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Clients", description = "Create, Read, Update and delete clients entities")
@RestController
@RequestMapping("api/v1/clients")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper mapper;

    @Operation(summary = "Get all registered clients", responses = {
            @ApiResponse(description = "Successfully fetched all clients",
                    responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientResource.class)))
    })
    @GetMapping
    public List<Client> fetchAll() {
        return clientService.fetchAll();
    }


    @Operation(summary = "Get a client by id", responses = {
            @ApiResponse(description = "Successfully fetched client by id",
                    responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientResource.class)))
    })
    @GetMapping("{id}")
    public ClientResource fetchById(@PathVariable Integer id) {
        return this.mapper.toResource(clientService.fetchById(id).get());
    }

    @Operation(summary = "Save a client", responses = {
            @ApiResponse(description = "Client successfully created",
                    responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientResource.class)))
    })
    @PostMapping("/company/{companyId}")
    public ClientResource save(@RequestBody CreateClientUserResource resource, @PathVariable Integer companyId){

        CreateClientResource clientResource = new CreateClientResource();
        clientResource.setGivenUser(clientService.obtainUserId(
                resource.getUsername(), resource.getEmail(), resource.getPassword(),
                resource.getPhoneNumber(), resource.getAddress(), resource.getCity(), companyId));
        /*if(givenPlan!=null){

        } else {*/
            clientResource.setGivenPlan(1);
        /*}*/

        return this.mapper.toResource(clientService.save(this.mapper.toModel(clientResource)));
    }

    @Operation(summary = "Update a client's plan", responses = {
            @ApiResponse(description = "Client's plan successfully updated",
                    responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientResource.class)))
    })
    @PutMapping("{id}/plan/{planId}")
    public ResponseEntity<ClientResource> updatePlan(@PathVariable Integer id, @RequestBody ClientPlanResource resource) {

        if (id.equals(clientService.fetchById(id).get().getId())) {
            if (clientService.existPlanByPlanId(resource.getGivenPlan())) {
                Client updatedClient = clientService.updatePlan(clientService.fetchById(id).get(), resource.getGivenPlan());
                ClientResource clientResource = mapper.toResource(updatedClient);
                return new ResponseEntity<>(clientResource, HttpStatus.OK);
            }
            else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }

    }
    @Operation(summary = "Fetch all class by client", responses = {
            @ApiResponse(description = "Client's classes successfully displayed",
                    responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientResource.class)))
    })
    @GetMapping("{id}/classes")
    public List<Optional<?>> fetchClientClass(@PathVariable Integer id) {
        return clientService.fetchClientClass(id);
    }


    @Operation(summary = "Join the class", responses = {
            @ApiResponse(description = "Join successfully created",
                    responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientResource.class)))
    })
    @PostMapping("{id}/join-class")
    public Optional<?> joinClass(@PathVariable Integer id, @RequestBody ClientClassResource classId) {
        return clientService.joinClass(id, classId.getClassId());
    }

    @Operation(summary = "Join the class", responses = {
            @ApiResponse(description = "Join successfully created",
                    responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientResource.class)))
    })
    @PostMapping("{id}/exit-class")
    public Optional<?> exitClass(@PathVariable Integer id, @RequestBody ClientClassResource classId) {
        return clientService.exitClass(id, classId.getClassId());
    }

    @Operation(summary = "update the limit for class departures", responses = {
            @ApiResponse(description = "Limit successfully updated",
                    responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientResource.class)))
    })
    @PostMapping("update-exit-class")
    public Optional<?> updateAllClassExits(@RequestBody UpdateClassExitsClient exitsClient) {
        return clientService.updateAllClassExits(exitsClient.getClassExits());
    }

    @Operation(summary = "update the class status", responses = {
            @ApiResponse(description = "Status successfully updated",
                    responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientResource.class)))
    })
    @PostMapping("/{id}/update-status-class")
    public Optional<?> updateClassStatus(@PathVariable Integer id, @RequestBody ClientClassResource classResource) {
        return clientService.updateClassStatus(clientService.fetchById(id).get(), classResource.getClassId());
    }



}
