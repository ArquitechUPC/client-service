package org.Arquitech.Gymrat.clientservice.Client.domain.persistence;

import feign.Param;
import org.Arquitech.Gymrat.clientservice.Client.domain.model.entity.Client;
import org.Arquitech.Gymrat.clientservice.Client.resource.client.ClientClassDetailResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO client_classes (client_id, class_id, status) VALUES (:clientId, :classId, :status)", nativeQuery = true)
    void insertClientClass(@Param("clientId") Integer clientId, @Param("classId") Integer classId, @Param("status") Boolean status);

    @Modifying
    @Transactional
    @Query(value = "UPDATE client_classes SET status = :status WHERE client_id = :clientId AND class_id = :classId", nativeQuery = true)
    void updateClientClass(@Param("clientId") Integer clientId, @Param("classId") Integer classId, @Param("status") Boolean status);

    @Query(value = "SELECT client_id as clientId, class_id as classId, status FROM client_classes WHERE client_id = :clientId AND class_id = :classId", nativeQuery = true)
    ClientClassDetailResource selectClientClass(@Param("clientId") Integer clientId, @Param("classId") Integer classId);

    @Query(value = "SELECT status FROM client_classes WHERE client_id = :clientId AND class_id = :classId", nativeQuery = true)
    boolean verifyClientClassStatus(@Param("clientId") Integer clientId, @Param("classId") Integer classId);

    List<Client> findByCompanyId(Integer companyId);
}


