package org.Arquitech.Gymrat.clientservice.Client.domain.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "description")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "measurement_id")
    private Measurement measurementGoal;

    @Column(nullable = false)
    private Boolean notificationEnabled;

    @Column(nullable = false)
    private Double targetValue;

    @Column(nullable = false)
    private Boolean progressNotificationSent = false;
}
