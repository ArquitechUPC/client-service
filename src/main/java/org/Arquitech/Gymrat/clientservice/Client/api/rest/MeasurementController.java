package org.Arquitech.Gymrat.clientservice.Client.api.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Measurement;
import org.Arquitech.Gymrat.clientservice.Client.domain.service.MeasurementService;
import org.Arquitech.Gymrat.clientservice.Client.mapping.MeasurementMapper;
import org.Arquitech.Gymrat.clientservice.Client.resource.measurement.CreateMeasurementResource;
import org.Arquitech.Gymrat.clientservice.Client.resource.measurement.MeasurementResource;
import org.Arquitech.Gymrat.clientservice.Client.resource.measurement.UpdateMeasurementResource;
//import org.Arquitech.Gymrat.clientservice.Client.resource.plan.PlanResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Measurement", description = "Create, Read, Update and delete measurements entities")
@RestController
@RequestMapping("api/v1/measurements")
@AllArgsConstructor
public class MeasurementController {
    private final MeasurementService measurementService;
    private final MeasurementMapper mapper;

    @Operation(summary = "Get all registered measurements", responses = {
            @ApiResponse(description = "Successfully fetched all measurements",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeasurementResource.class)))
    })
    @GetMapping
    public List<Measurement> fetchAll() {
        return measurementService.fetchAll();
    }
    @Operation(summary = "Get all client's measurements", responses = {
            @ApiResponse(description = "Successfully fetched all client's measurements",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeasurementResource.class)))
    })
    @GetMapping("/client/{clientId}")
    public List<Measurement> fetchMeasurementByClient(@PathVariable Integer clientId) {
        return measurementService.fetchByClient(clientId);
    }

    @Operation(summary = "Get all registered measurements", responses = {
            @ApiResponse(description = "Successfully fetched all measurements",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeasurementResource.class)))
    })
    @GetMapping("{id}")
    public MeasurementResource fetchById(@PathVariable Integer id){
        return this.mapper.toResource(measurementService.fetchById(id).get());
    }

    @Operation(summary = "Save a measurement", responses = {
            @ApiResponse(description = "Measurement successfully created",
                    responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeasurementResource.class)))
    })
    @PostMapping("client/{clientId}")
    public MeasurementResource save(@RequestBody CreateMeasurementResource resource, @PathVariable Integer clientId){
        return this.mapper.toResource(measurementService.save(this.mapper.toModel(resource), clientId));
    }

    @Operation(summary = "Update a measurement by id", responses = {
            @ApiResponse(description = "Measurement successfully updated",
                    responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeasurementResource.class)))
    })
    @PutMapping("{id}")
    public ResponseEntity<MeasurementResource> update(@PathVariable Integer id, @RequestBody UpdateMeasurementResource resource){
        if(id.equals(resource.getId())) {
            MeasurementResource measurementResource = mapper.toResource(
                    measurementService.update(mapper.toModel(resource)));
            return new ResponseEntity<>(measurementResource, HttpStatus.OK);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Delete a measurement by id", responses = {
            @ApiResponse(description = "Successfully deleted measurement by id",
                    responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MeasurementResource.class)))
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        if(measurementService.deleteById(id)){
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


}
