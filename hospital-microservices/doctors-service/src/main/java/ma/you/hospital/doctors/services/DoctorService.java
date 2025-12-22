package ma.you.hospital.doctors.services;

import ma.you.hospital.doctors.domain.Doctor;
import ma.you.hospital.doctors.repositories.DoctorRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Service
@Transactional
public class DoctorService {

    private final DoctorRepository repo;

    public DoctorService(DoctorRepository repo) {
        this.repo = repo;
    }

    public Page<Doctor> list(String q, Pageable pageable) {
        String query = (q == null || q.isBlank()) ? null : q.trim();
        return repo.search(query, pageable);
    }

    public Doctor get(Long id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Doctor %d not found".formatted(id))
        );
    }

    public Doctor create(Doctor d) {
        normalize(d);

        if (d.getEmail() != null && repo.existsByEmail(d.getEmail())) {
            throw new BusinessException("Email already used");
        }
        return repo.save(d);
    }

    public Doctor update(Long id, Consumer<Doctor> updater) {
        Doctor d = get(id);
        updater.accept(d);

        normalize(d);

        if (d.getEmail() != null && repo.existsByEmailAndIdNot(d.getEmail(), id)) {
            throw new BusinessException("Email already used");
        }
        return repo.save(d);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Doctor %d not found".formatted(id));
        }
        repo.deleteById(id);
    }

    private void normalize(Doctor d) {
        if (d.getEmail() != null) {
            String email = d.getEmail().trim();
            d.setEmail(email.isBlank() ? null : email);
        }
        if (d.getFirstName() != null) d.setFirstName(d.getFirstName().trim());
        if (d.getLastName() != null) d.setLastName(d.getLastName().trim());
        if (d.getSpecialty() != null) d.setSpecialty(d.getSpecialty().trim());
        if (d.getPhone() != null) d.setPhone(d.getPhone().trim());
        if (d.getAddress() != null) d.setAddress(d.getAddress().trim());
    }
}
