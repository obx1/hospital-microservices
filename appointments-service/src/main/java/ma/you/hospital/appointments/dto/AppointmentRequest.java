package ma.you.hospital.appointments.dto;

import lombok.Data;

@Data
public class AppointmentRequest {
    private Long doctorId;
    private Long patientId;
    private String date;
    private String time;
}
