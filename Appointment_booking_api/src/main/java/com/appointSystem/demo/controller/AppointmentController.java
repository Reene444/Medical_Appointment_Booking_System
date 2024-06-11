package com.appointSystem.demo.controller;


import com.appointSystem.demo.model.Account;
import com.appointSystem.demo.model.Appointment;

import com.appointSystem.demo.payload.appointment.AppointmentPayloadDTO;
import com.appointSystem.demo.payload.appointment.AppointmentViewDTO;
import com.appointSystem.demo.service.AccountService;
import com.appointSystem.demo.service.AppointmentService;
import com.appointSystem.demo.util.constants.AppointmentError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Appointment Controller", description = "Controller for appointment management")
@Slf4j
public class AppointmentController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AppointmentService appointmentService;


    @PostMapping(value = "/appointments",consumes = "application/json",produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400",description = "Please add valid name a description")
    @ApiResponse(responseCode = "201",description = "Account added")
    @Operation(summary = "Add an Appointment api")

    public ResponseEntity<AppointmentViewDTO> addAppointment(@RequestBody AppointmentPayloadDTO appointmentPayloadDTO){

        try{
            System.out.println(appointmentPayloadDTO);
            Appointment appointment=new Appointment();
            Optional<Account>patient=accountService.findByID( appointmentPayloadDTO.getPatient_id());
            Optional<Account>doctor =accountService.findByID( appointmentPayloadDTO.getDoctor_id());
            if(patient.isPresent()&&doctor.isPresent()){
                appointment.setPatient(patient.get());
                appointment.setDoctor(doctor.get());
                appointment.setDate(appointmentPayloadDTO.getDate());
                appointment.setTime(appointmentPayloadDTO.getTime());
                appointment=appointmentService.save(appointment);

                AppointmentViewDTO AppointmentViewDTO=new AppointmentViewDTO(appointment.getId(),appointment.getDoctor().getName(),appointment.getPatient().getName(), appointment.getDate(),appointment.getTime());
                return  ResponseEntity.ok(AppointmentViewDTO);
            }
            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

        }catch (Exception e){
            log.debug(AppointmentError.ADD_APPOINTMENT_ERROR.toString() +": "+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }
    @GetMapping(value = "/appointments",produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200",description = " List of Appointments")
    @ApiResponse(responseCode = "401",description = "Token missing")
    @ApiResponse(responseCode = "403",description = "Token error")
    @Operation(summary = "List of Appointment api")

    public List<AppointmentViewDTO> appointments(){
        List<AppointmentViewDTO>appointments=new ArrayList<>();
        for(Appointment appointment:appointmentService.findAll()){
            appointments.add(new AppointmentViewDTO(appointment.getId(),appointment.getDoctor().getName(),appointment.getPatient().getName(),appointment.getDate(),appointment.getTime()));
        }
        return appointments;
    }


    @GetMapping(value = "/appointments/{appointment_id}",produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200",description = " List of Appointments")
    @ApiResponse(responseCode = "401",description = "Token missing")
    @ApiResponse(responseCode = "403",description = "Token error")
    @Operation(summary = "List Appointment by Appointment ID")

    public ResponseEntity<AppointmentViewDTO> appointments_by_id(@PathVariable long appointment_id){
        Optional<Appointment>optionalAppointment=appointmentService.findById(appointment_id);
        Appointment appointment;
        if(optionalAppointment.isPresent()){
            appointment=optionalAppointment.get();
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

        AppointmentViewDTO appointmentViewDTO=new AppointmentViewDTO(appointment.getId(),appointment.getDoctor().getName(),appointment.getPatient().getName(),appointment.getDate(),appointment.getTime());
        return ResponseEntity.ok(appointmentViewDTO);
    }



    @DeleteMapping(value = "appointments/{appointment_id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "202", description = "Appointment deleted")
    @Operation(summary = "delete a photo")

    public ResponseEntity<String> delete_Appointment(@PathVariable long appointment_id) {
        try {

            Optional<Appointment> optionaAppointment = appointmentService.findById(appointment_id);
            Appointment appointment;
            if (optionaAppointment.isPresent()) {
                appointment = optionaAppointment.get();

            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }


            appointmentService.deleteAppointment(appointment);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);


        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}


