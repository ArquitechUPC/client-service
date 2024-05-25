package org.Arquitech.Gymrat.clientservice.Client.resource.client;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class ClientClassResource {
    @NotNull
    private Integer classId;
}
