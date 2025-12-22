package ma.you.hospital.appointments.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments", indexes = {
        @Index(name = "idx_appointments_doctor", columnList = "doctorId"),
        @Index(name = "idx_appointments_patient", columnList = "patientId"),
        @Index(name = "idx_appointments_date", columnList = "date")
})
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long doctorId;

    @Column(nullable = false)
    private Long patientId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AppointmentStatus status;
}
