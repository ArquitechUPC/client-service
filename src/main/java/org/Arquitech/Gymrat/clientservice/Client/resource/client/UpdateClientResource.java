package org.Arquitech.Gymrat.clientservice.Client.resource.client;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClientResource {

    @NotNull
    @NotBlank
    private Integer givenUser;

    @NotNull
    @NotBlank
    private Integer givenPlan;
}
