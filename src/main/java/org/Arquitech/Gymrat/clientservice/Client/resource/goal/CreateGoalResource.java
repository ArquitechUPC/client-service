package org.Arquitech.Gymrat.clientservice.Client.resource.goal;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Measurement;
import org.Arquitech.Gymrat.clientservice.Client.resource.measurement.CreateMeasurementResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CreateGoalResource {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @NotNull
    private CreateMeasurementResource measurement;

}
