package org.Arquitech.Gymrat.clientservice.Client.domain.persistence;

import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
