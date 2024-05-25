package org.Arquitech.Gymrat.clientservice.Client.resource.measurement;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMeasurementResource {

    @NotNull
    @NotBlank
    @Min(1)
    private Integer id;

    @NotNull
    @NotBlank
    private Double weight;

    private String comment;

    private Double chestCircumference;

    private Double waistCircumference;

    private Double hipCircumference;

    private Double armCircumference;

    private Double legCircumference;

}
