package ma.you.hospital.doctors.repositories;

import ma.you.hospital.doctors.domain.Doctor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("""
      SELECT d FROM Doctor d
      WHERE (:q IS NULL OR LOWER(d.firstName)  LIKE LOWER(CONCAT('%', :q, '%'))
                     OR LOWER(d.lastName)   LIKE LOWER(CONCAT('%', :q, '%'))
                     OR LOWER(d.specialty)  LIKE LOWER(CONCAT('%', :q, '%'))
                     OR LOWER(d.email)      LIKE LOWER(CONCAT('%', :q, '%')))
      """)
    Page<Doctor> search(String q, Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}
