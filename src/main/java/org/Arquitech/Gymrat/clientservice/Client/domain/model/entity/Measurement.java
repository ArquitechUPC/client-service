package org.Arquitech.Gymrat.clientservice.Client.domain.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "measurements")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "comment")
    private String comment;

    @Column(name = "chest_circumference")
    private Double chestCircumference;

    @Column(name = "waist_circumference")
    private Double waistCircumference;

    @Column(name = "hip_circumference")
    private Double hipCircumference;

    @Column(name = "arm_circumference")
    private Double armCircumference;

    @Column(name = "leg_circumference")
    private Double legCircumference;

    @PrePersist
    public void prePersist() {
        date = LocalDate.now();
    }
}
