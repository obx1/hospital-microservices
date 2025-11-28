package ma.you.hospital.appointments.repositories;

import ma.you.hospital.appointments.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
