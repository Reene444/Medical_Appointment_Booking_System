package com.appointSystem.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @ManyToOne
    @JoinColumn(name="patient_id",referencedColumnName = "id",nullable = false)
    private Account patient;
    @ManyToOne
    @JoinColumn(name="doctor_id",referencedColumnName = "id",nullable = false)
    private Account doctor;

    private String date;

    private String time;


}
