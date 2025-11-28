package ma.you.hospital.billing.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.you.hospital.billing.entities.Bill;
import ma.you.hospital.billing.model.DoctorDTO;
import ma.you.hospital.billing.model.PatientDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillResponseDTO {

    private Bill bill;
    private DoctorDTO doctor;
    private PatientDTO patient;
}
