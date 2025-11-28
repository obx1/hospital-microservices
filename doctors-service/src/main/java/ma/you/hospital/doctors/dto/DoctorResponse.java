package ma.you.hospital.doctors.dto;

import java.time.Instant;

public record DoctorResponse(
        Long id, String firstName, String lastName, String specialty,
        String email, String phone, String address,
        Instant createdAt, Instant updatedAt
) {}
