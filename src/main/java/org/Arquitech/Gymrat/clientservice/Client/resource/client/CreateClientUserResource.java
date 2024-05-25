package org.Arquitech.Gymrat.clientservice.Client.resource.client;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientUserResource {


    @NotNull
    String username;
    @NotNull
    String email;
    @NotNull
    String password;
    String gymName;
    String phoneNumber;
    String address;
    String city;
}
