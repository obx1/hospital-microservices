package ma.you.hospital.appointments.repositories;

import ma.you.hospital.appointments.domain.Appointment;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
        SELECT a FROM Appointment a
        WHERE (:doctorId IS NULL OR a.doctorId = :doctorId)
          AND (:patientId IS NULL OR a.patientId = :patientId)
          AND (:status IS NULL OR LOWER(CAST(a.status as string)) = LOWER(:status))
        """)
    Page<Appointment> search(Long doctorId, Long patientId, String status, Pageable pageable);

    boolean existsByDoctorIdAndDateAndTime(Long doctorId, LocalDate date, LocalTime time);
}
