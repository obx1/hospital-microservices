package ma.you.hospital.billing.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentDTO {
    private Long id;
    private Long doctorId;
    private Long patientId;
    private LocalDate date;
    private String description;
    private String status;
}
