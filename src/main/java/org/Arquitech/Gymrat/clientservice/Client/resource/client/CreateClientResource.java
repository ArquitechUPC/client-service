package org.Arquitech.Gymrat.clientservice.Client.resource.client;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientResource {
    @NotNull
    private Integer givenUser;

    @NotNull
    private Integer givenPlan;

}
