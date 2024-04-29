package org.Arquitech.Gymrat.clientservice.Client.resource.goal;

import lombok.*;
import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Measurement;

import java.util.Date;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class GoalResource {

    private Integer id;

    private String name;

    private String description;

    private Date startDate;

    private Date endDate;

    private Measurement measurementGoal;

}
