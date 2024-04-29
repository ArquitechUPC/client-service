package org.Arquitech.Gymrat.clientservice.Client.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-gateway", url = "http://localhost:8080", path = "/auth")
public interface UserClient {
    @GetMapping("/exist-user/{userId}")
    boolean existUserById(@PathVariable("userId") Integer userId);
}



