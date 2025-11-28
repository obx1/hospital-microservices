package ma.you.hospital.billing.web.dto;

import lombok.Data;

@Data
public class BillRequestDTO {
    private Long appointmentId;
    private Double amount;
    private String description;
}
