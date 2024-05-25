package org.Arquitech.Gymrat.clientservice.Client.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Table(name = "clients")
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer givenUser;

    @NotNull
    private Integer givenPlan;

    @NotNull
    private Integer classExits;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private List<Goal> goals;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Measurement> measurements;

    @ElementCollection
    @CollectionTable(name = "client_classes", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "class_id")
    private Set<Integer> classIds = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "client_classes", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "status")
    private Set<Boolean> status = new HashSet<>();

    @NotNull
    @Column(name = "company_id")
    private Integer companyId;
}
