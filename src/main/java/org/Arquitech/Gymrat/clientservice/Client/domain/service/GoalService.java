package org.Arquitech.Gymrat.clientservice.Client.domain.service;

import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Goal;

import java.util.List;
import java.util.Optional;

public interface GoalService {
    List<Goal> fetchAll();
    List<Goal> fetchByClient(Integer givenClientId);
    Optional<Goal> fetchById(Integer Id);
    Goal save(Goal goal, Integer givenClientId);
    Goal update(Goal goal);
    boolean deleteById(Integer id);
}
