package ma.you.hospital.appointments.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "archived_appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchivedAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // ID de la ligne d’archive

    @Column(name = "appointment_id")
    private Long appointmentId;  // ID du rendez-vous original

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

    @Column(name = "appointment_time")
    private LocalTime appointmentTime;

    private String status;
}
