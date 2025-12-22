package ma.you.hospital.billing.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "bills")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long appointmentId;
    private Long doctorId;
    private Long patientId;

    private Double amount;

    private LocalDate billDate;

    @Column(length = 255)
    private String description;
}
