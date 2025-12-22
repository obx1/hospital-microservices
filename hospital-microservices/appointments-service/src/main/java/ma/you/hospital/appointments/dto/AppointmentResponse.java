package ma.you.hospital.appointments.dto;

import lombok.Builder;

@Builder
public record AppointmentResponse(
        Long id,
        Long doctorId,
        Long patientId,
        String date,
        String time,
        String status,
        Object doctor,
        Object patient
) {}
