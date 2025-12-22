package ma.you.hospital.appointments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppointmentRequest {
    @NotNull
    private Long doctorId;

    @NotNull
    private Long patientId;

    @NotBlank // yyyy-MM-dd
    private String date;

    @NotBlank // HH:mm ou HH:mm:ss
    private String time;

    // optionnel: SCHEDULED/CONFIRMED/CANCELLED/COMPLETED
    private String status;
}
