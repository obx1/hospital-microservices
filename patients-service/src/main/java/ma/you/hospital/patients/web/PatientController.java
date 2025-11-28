package ma.you.hospital.patients.web;
import ma.you.hospital.patients.services.PatientService;
import ma.you.hospital.patients.web.dto.PatientRequest;
import ma.you.hospital.patients.web.dto.PatientResponse;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @GetMapping
    public Page<PatientResponse> list(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "lastName,asc") String sort
    ) {
        Sort s = Sort.by(sort.split(",")[0]);
        if (sort.endsWith(",desc")) s = s.descending(); else s = s.ascending();
        Pageable pageable = PageRequest.of(page, size, s);
        return service.list(q, pageable).map(PatientMapper::toResponse);
    }

    @GetMapping("/{id}")
    public PatientResponse get(@PathVariable Long id) {
        return PatientMapper.toResponse(service.get(id));
    }

    @PostMapping
    public ResponseEntity<PatientResponse> create(@Valid @RequestBody PatientRequest req) {
        var saved = service.create(PatientMapper.toEntity(req));
        return ResponseEntity.status(HttpStatus.CREATED).body(PatientMapper.toResponse(saved));
    }

    @PutMapping("/{id}")
    public PatientResponse update(@PathVariable Long id, @Valid @RequestBody PatientRequest req) {
        var updated = service.update(id, p -> PatientMapper.update(p, req));
        return PatientMapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
