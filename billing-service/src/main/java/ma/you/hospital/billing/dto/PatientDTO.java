package ma.you.hospital.billing.dto;

import lombok.Data;

@Data
public class PatientDTO {
    private Long id;
    private String name;      // idem : adapte aux champs de ton patient-service
    private String email;
}
