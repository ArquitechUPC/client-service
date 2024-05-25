package org.Arquitech.Gymrat.clientservice.Client.api.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Goal;
import org.Arquitech.Gymrat.clientservice.Client.domain.service.GoalService;
import org.Arquitech.Gymrat.clientservice.Client.mapping.GoalMapper;
import org.Arquitech.Gymrat.clientservice.Client.resource.goal.CreateGoalResource;
import org.Arquitech.Gymrat.clientservice.Client.resource.goal.GoalResource;
import org.Arquitech.Gymrat.clientservice.Client.resource.goal.UpdateGoalResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Goals", description = "Create, Read, Update and delete goals entities")
@RestController
@RequestMapping("api/v1/goals")
@AllArgsConstructor
public class GoalController {

    private final GoalService goalService;
    private final GoalMapper mapper;

    @Operation(summary = "Get all registered goals", responses = {
            @ApiResponse(description = "Successfully fetched all goals",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GoalResource.class)))
    })
    @GetMapping
    public List<Goal> fetchAll() {
        return goalService.fetchAll();
    }

    @Operation(summary = "Get a goal by id", responses = {
            @ApiResponse(description = "Successfully fetched goal by id",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GoalResource.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<GoalResource> fetchById(@PathVariable Integer id) {
        return goalService.fetchById(id)
                .map(mapper::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Save a goal", responses = {
            @ApiResponse(description = "Goal successfully created",
                    responseCode = "201",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GoalResource.class)))
    })
    @PostMapping("client/{clientId}")
    public GoalResource save(@RequestBody CreateGoalResource resource, @PathVariable Integer clientId) {
        return mapper.toResource(goalService.save(mapper.toModel(resource), clientId));
    }


    @Operation(summary = "Update a goal by id", responses = {
            @ApiResponse(description = "Goal successfully updated",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GoalResource.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<GoalResource> update(@PathVariable Integer id, @RequestBody UpdateGoalResource resource) {
        if(id.equals(resource.getId())) {
            GoalResource goalResource = mapper.toResource(
                    goalService.update(mapper.toModel(resource)));
            return ResponseEntity.ok(goalResource);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Delete a goal by id", responses = {
            @ApiResponse(description = "Successfully deleted goal by id",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GoalResource.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (goalService.deleteById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

