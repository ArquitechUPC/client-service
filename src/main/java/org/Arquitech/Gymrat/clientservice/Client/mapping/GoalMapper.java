package org.Arquitech.Gymrat.clientservice.Client.mapping;

import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Goal;
import org.Arquitech.Gymrat.clientservice.Client.resource.goal.CreateGoalResource;
import org.Arquitech.Gymrat.clientservice.Client.resource.goal.GoalResource;
import org.Arquitech.Gymrat.clientservice.Client.resource.goal.UpdateGoalResource;
import org.Arquitech.Gymrat.clientservice.Shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class GoalMapper implements Serializable {
    @Autowired
    EnhancedModelMapper mapper;
    public Goal toModel(CreateGoalResource resource){ return this.mapper.map(resource, Goal.class);}
    public Goal toModel(UpdateGoalResource resource){ return this.mapper.map(resource, Goal.class);}
    public GoalResource toResource(Goal goal){ return this.mapper.map(goal, GoalResource.class);}

}
