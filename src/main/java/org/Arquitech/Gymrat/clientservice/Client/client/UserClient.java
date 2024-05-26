package org.Arquitech.Gymrat.clientservice.Client.client;

import org.Arquitech.Gymrat.clientservice.Client.resource.client.CreateClientUserResource;
import org.Arquitech.Gymrat.clientservice.Client.resource.client.RequestUserCompany;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", url = "http://localhost:8080", path = "/api/v1/auth")
public interface UserClient {
    @GetMapping("/exist-user/{userId}")
    boolean existUserById(@PathVariable("userId") Integer userId);

    @PostMapping("/register-client")
    Integer ObtainUserId(@RequestBody RequestUserCompany request);
}



