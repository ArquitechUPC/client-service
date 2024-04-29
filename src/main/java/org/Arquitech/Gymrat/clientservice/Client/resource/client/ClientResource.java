package org.Arquitech.Gymrat.clientservice.Client.resource.client;

import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class ClientResource {
    private Integer id;
    private Integer givenUser;
    private Integer givenPlan;
}
