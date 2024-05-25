package org.Arquitech.Gymrat.clientservice.Client.resource.client;

import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class ClientClassDetailResource {
    private Integer clientId;
    private Integer classId;
    private Boolean status;
}
