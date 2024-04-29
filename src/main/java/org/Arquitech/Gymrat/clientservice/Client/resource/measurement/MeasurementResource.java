package org.Arquitech.Gymrat.clientservice.Client.resource.measurement;

import lombok.*;


@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementResource {
    private Integer id;

    private Double weight;

    private String comment;

    private Double chestCircumference;

    private Double waistCircumference;

    private Double hipCircumference;

    private Double armCircumference;

    private Double legCircumference;

}
