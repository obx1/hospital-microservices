package ma.you.hospital.doctors.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.you.hospital.doctors.domain.Doctor;
import ma.you.hospital.doctors.repository.DoctorRepository;
import ma.you.hospital.doctors.web.dto.DoctorRequest;
import ma.you.hospital.doctors.web.dto.DoctorResponse;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@Tag(name="Doctors", description="CRUD des médecins")
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorRepository repo;
    public DoctorController(DoctorRepository repo){ this.repo = repo; }

    private static DoctorResponse toDto(Doctor d){
        return new DoctorResponse(d.getId(), d.getFirstName(), d.getLastName(),
                d.getSpecialty(), d.getEmail(), d.getPhone(), d.getAddress(),
                d.getCreatedAt(), d.getUpdatedAt());
    }

    @Operation(summary = "Lister les médecins")
    @GetMapping
    public Page<DoctorResponse> list(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "lastName,asc") String sort) {

        Sort sortSpec;
        String[] parts = sort.split(",");
        if (parts.length >= 2 && "desc".equalsIgnoreCase(parts[1])) {
            sortSpec = Sort.by(Sort.Order.desc(parts[0]));
        } else {
            sortSpec = Sort.by(Sort.Order.asc(parts[0]));
        }

        Pageable pageable = PageRequest.of(page, size, sortSpec);
        return repo.findAll(pageable).map(DoctorController::toDto);
    }

    @Operation(summary="Créer un médecin")
    @PostMapping
    public DoctorResponse create(@RequestBody @Valid DoctorRequest req){
        var d = new Doctor();
        d.setFirstName(req.firstName()); d.setLastName(req.lastName());
        d.setSpecialty(req.specialty()); d.setEmail(req.email());
        d.setPhone(req.phone()); d.setAddress(req.address());
        return toDto(repo.save(d));
    }

    @Operation(summary="Détail médecin")
    @GetMapping("/{id}")
    public DoctorResponse get(@PathVariable Long id){
        var d = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return toDto(d);
    }

    @Operation(summary="Mettre à jour")
    @PutMapping("/{id}")
    public DoctorResponse update(@PathVariable Long id, @RequestBody @Valid DoctorRequest req){
        var d = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        d.setFirstName(req.firstName()); d.setLastName(req.lastName());
        d.setSpecialty(req.specialty()); d.setEmail(req.email());
        d.setPhone(req.phone()); d.setAddress(req.address());
        return toDto(repo.save(d));
    }

    @Operation(summary="Supprimer")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ repo.deleteById(id); }
}
