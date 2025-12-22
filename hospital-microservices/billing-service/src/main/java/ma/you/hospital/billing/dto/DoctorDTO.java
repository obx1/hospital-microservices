package ma.you.hospital.billing.dto;

import lombok.Data;

@Data
public class DoctorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String specialty;
    private String email;
    private String phone;
    private String address;
}
