package ma.you.hospital.billing.repositories;

import ma.you.hospital.billing.domain.Bill;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("""
        SELECT b FROM Bill b
        WHERE (:patientId IS NULL OR b.patientId = :patientId)
          AND (:doctorId IS NULL OR b.doctorId = :doctorId)
          AND (:appointmentId IS NULL OR b.appointmentId = :appointmentId)
        """)
    Page<Bill> search(Long patientId, Long doctorId, Long appointmentId, Pageable pageable);

    boolean existsByAppointmentId(Long appointmentId);
}
