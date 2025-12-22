package ma.you.hospital.patients.services;

import ma.you.hospital.patients.domain.Patient;
import ma.you.hospital.patients.repositories.PatientRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Service
@Transactional
public class PatientService {

    private final PatientRepository repo;

    public PatientService(PatientRepository repo) {
        this.repo = repo;
    }

    public Page<Patient> list(String q, Pageable pageable) {
        String query = (q == null || q.isBlank()) ? null : q.trim();
        return repo.search(query, pageable);
    }

    public Patient get(Long id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Patient %d not found".formatted(id))
        );
    }

    public Patient create(Patient p) {
        normalize(p);

        if (p.getEmail() != null && repo.existsByEmail(p.getEmail())) {
            throw new BusinessException("Email already used");
        }
        return repo.save(p);
    }

    public Patient update(Long id, Consumer<Patient> updater) {
        var p = get(id);
        updater.accept(p);

        normalize(p);

        if (p.getEmail() != null && repo.existsByEmailAndIdNot(p.getEmail(), id)) {
            throw new BusinessException("Email already used");
        }
        return repo.save(p);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Patient %d not found".formatted(id));
        }
        repo.deleteById(id);
    }

    private void normalize(Patient p) {
        if (p.getEmail() != null) {
            String email = p.getEmail().trim();
            p.setEmail(email.isBlank() ? null : email);
        }
        if (p.getFirstName() != null) p.setFirstName(p.getFirstName().trim());
        if (p.getLastName() != null) p.setLastName(p.getLastName().trim());
        if (p.getPhone() != null) p.setPhone(p.getPhone().trim());
        if (p.getAddress() != null) p.setAddress(p.getAddress().trim());
    }
}
