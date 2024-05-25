package org.Arquitech.Gymrat.clientservice.Client.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "plan-service", path = "localhost:8081/plans")
public interface PlanClient {
    @GetMapping("/exist-plan/{planId}")
    boolean existPlanById(@PathVariable Integer planId);
}
