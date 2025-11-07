package ma.you.hospital.patients.web.dto;

import ma.you.hospital.patients.domain.Gender;
import java.time.*;

public record PatientResponse(
        Long id,
        String firstName,
        String lastName,
        Gender gender,
        LocalDate birthDate,
        String email,
        String phone,
        String address,
        Instant createdAt,
        Instant updatedAt
) {}
