package com.appointSystem.demo.payload.appointment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentPayloadDTO {
    @NotBlank
    @Schema(description = " patient_id",example = "1",requiredMode = Schema.RequiredMode.REQUIRED)
    private long patient_id;

    @NotBlank
    @Schema(description = " doctor_id",example = "2",requiredMode = Schema.RequiredMode.REQUIRED)
    private long doctor_id;
    @NotBlank
    @Schema(description = " date",example = "1.3",requiredMode = Schema.RequiredMode.REQUIRED)
    private String date;

    @NotBlank
    @Schema(description = " time",example = "12:30",requiredMode = Schema.RequiredMode.REQUIRED)
    private String time;
}
