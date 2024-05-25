package org.Arquitech.Gymrat.clientservice.Client.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

//@FeignClient(name = "admin-service", url = "http://localhost:8080",path = "/api/v1/admin")
public interface AdminClient {

    /*@GetMapping("/company/by-user/{userId}")
    Integer getCompanyIdByUserId(@PathVariable("userId") Integer userId);*/

}
