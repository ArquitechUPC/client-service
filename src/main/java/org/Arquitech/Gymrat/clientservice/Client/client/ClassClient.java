package org.Arquitech.Gymrat.clientservice.Client.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "class-service", url = "http://localhost:8080", path = "/api/v1/classes")
public interface ClassClient {
    @GetMapping("/{id}")
    Optional<?> fetchClassById(@PathVariable Integer id);
    @GetMapping
    List<Optional<?>> fetchAllClass();
    @PostMapping("availability-class/{id}")
    boolean availabilityClass(@PathVariable Integer id);

    @PostMapping("update-vacancy")
    Optional<?> updateVacancy(@RequestBody Integer classId);

    @PostMapping("increases-vacancy")
    Optional<?> increasesVacancy(@RequestBody Integer classId);
}
