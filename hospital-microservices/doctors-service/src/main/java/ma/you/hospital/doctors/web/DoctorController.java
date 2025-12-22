package ma.you.hospital.doctors.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.you.hospital.doctors.dto.DoctorRequest;
import ma.you.hospital.doctors.dto.DoctorResponse;
import ma.you.hospital.doctors.services.DoctorService;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Tag(name="Doctors", description="CRUD des médecins")
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService service;

    public DoctorController(DoctorService service){
        this.service = service;
    }

    @Operation(summary = "Lister les médecins (avec recherche)")
    @GetMapping
    public Page<DoctorResponse> list(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "lastName,asc") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        return service.list(q, pageable).map(DoctorMapper::toResponse);
    }

    @Operation(summary="Créer un médecin")
    @PostMapping
    public ResponseEntity<DoctorResponse> create(@RequestBody @Valid DoctorRequest req){
        var saved = service.create(DoctorMapper.toEntity(req));
        return ResponseEntity.status(HttpStatus.CREATED).body(DoctorMapper.toResponse(saved));
    }

    @Operation(summary="Détail médecin")
    @GetMapping("/{id}")
    public DoctorResponse get(@PathVariable Long id){
        return DoctorMapper.toResponse(service.get(id));
    }

    @Operation(summary="Mettre à jour")
    @PutMapping("/{id}")
    public DoctorResponse update(@PathVariable Long id, @RequestBody @Valid DoctorRequest req){
        var updated = service.update(id, d -> DoctorMapper.update(d, req));
        return DoctorMapper.toResponse(updated);
    }

    @Operation(summary="Supprimer")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) return Sort.by("lastName").ascending();
        String[] parts = sort.split(",");
        String field = parts[0].trim();
        String dir = (parts.length > 1) ? parts[1].trim().toLowerCase() : "asc";
        return "desc".equals(dir) ? Sort.by(field).descending() : Sort.by(field).ascending();
    }
}
