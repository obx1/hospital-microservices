package ma.you.hospital.billing.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BillRequestDTO {

    @NotNull
    private Long appointmentId;

    @NotNull
    @Positive
    private Double amount;

    @Size(max = 255)
    private String description;
}
