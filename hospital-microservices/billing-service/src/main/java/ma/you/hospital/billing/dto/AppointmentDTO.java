package ma.you.hospital.billing.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentDTO {
    private Long id;
    private Long doctorId;
    private Long patientId;
    private LocalDate date;
    private LocalTime time;
    private String status;
}
