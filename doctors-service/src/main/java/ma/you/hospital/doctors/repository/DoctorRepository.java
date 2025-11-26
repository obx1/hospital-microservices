package ma.you.hospital.doctors.repository;

import ma.you.hospital.doctors.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {}
