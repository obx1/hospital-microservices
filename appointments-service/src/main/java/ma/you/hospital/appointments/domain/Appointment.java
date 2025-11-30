package ma.you.hospital.appointments.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long doctorId;
    private Long patientId;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "time")
    private LocalTime time;

    private String status;
}
