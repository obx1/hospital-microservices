package ma.you.hospital.appointments.repositories;

import ma.you.hospital.appointments.domain.ArchivedAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivedAppointmentRepository extends JpaRepository<ArchivedAppointment, Long> {
}
