package com.appointSystem.demo.payload.appointment;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentViewDTO {

    @NotBlank
    @Schema(description = "appointment",example = "1",requiredMode = Schema.RequiredMode.REQUIRED)
    private long appointment_id;
    @NotBlank
    @Schema(description = "doctor name",example = "doctor1",requiredMode = Schema.RequiredMode.REQUIRED)
    private String doctor_name;

    @NotBlank
    @Schema(description = "patient",example = "patient1")
    private String patient_name;
    @NotBlank
    @Schema(description = "date",example = "1.3",requiredMode = Schema.RequiredMode.REQUIRED)
    private String date;

    @NotBlank
    @Schema(description = "time",example = "12:30")
    private String time;


}
