package ma.you.hospital.patients.services;

import ma.you.hospital.patients.domain.Patient;
import ma.you.hospital.patients.repositories.PatientRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PatientService {

    private final PatientRepository repo;

    public PatientService(PatientRepository repo) {
        this.repo = repo;
    }

    public Page<Patient> list(String q, Pageable pageable) {
        return repo.search(q, pageable);
    }

    public Patient get(Long id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Patient %d not found".formatted(id))
        );
    }

    public Patient create(Patient p) {
        if (p.getEmail() != null && repo.existsByEmail(p.getEmail())) {
            throw new BusinessException("Email already used");
        }
        return repo.save(p);
    }

    public Patient update(Long id, java.util.function.Consumer<Patient> updater) {
        var p = get(id);
        updater.accept(p);
        if (p.getEmail() != null && repo.existsByEmail(p.getEmail()) && !p.getId().equals(id)) {
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
}
