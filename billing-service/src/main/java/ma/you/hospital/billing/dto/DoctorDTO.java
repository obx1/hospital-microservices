package ma.you.hospital.billing.dto;

import lombok.Data;

@Data
public class DoctorDTO {
    private Long id;
    private String name;      // ou firstName/lastName selon ton service
    private String email;
    private String speciality;
}
