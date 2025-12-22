package ma.you.hospital.doctors.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DoctorRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String specialty,
        @Email String email,
        String phone,
        String address
) {}
