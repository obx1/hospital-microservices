package ma.you.hospital.billing.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;     // enum côté patient-service, ici String OK
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String address;
}
