package com.appointSystem.demo.service;

import com.appointSystem.demo.model.Appointment;
import com.appointSystem.demo.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment save(Appointment appointment){
        return appointmentRepository.save(appointment);
    }

    public List<Appointment>findAll(){
        return appointmentRepository.findAll();
    }

    public Optional<Appointment>findById(long id){
        return appointmentRepository.findById(id);
    }

    public void deleteAppointment(Appointment appointment){
        appointmentRepository.delete(appointment);
    }
}

