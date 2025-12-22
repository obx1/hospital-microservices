package ma.you.hospital.appointments.web;

import jakarta.validation.Valid;
import ma.you.hospital.appointments.dto.*;
import ma.you.hospital.appointments.services.AppointmentService;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping
    public Page<AppointmentResponse> list(
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "false") boolean includeDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date,desc") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        return service.list(doctorId, patientId, status, pageable).map(ap -> {
            Object doctor = includeDetails ? service.fetchDoctor(ap.getDoctorId()).orElse(null) : null;
            Object patient = includeDetails ? service.fetchPatient(ap.getPatientId()).orElse(null) : null;
            return AppointmentMapper.toResponse(ap, doctor, patient);
        });
    }

    @GetMapping("/{id}")
    public AppointmentResponse get(@PathVariable Long id,
                                   @RequestParam(defaultValue = "false") boolean includeDetails) {
        var ap = service.getById(id);
        Object doctor = includeDetails ? service.fetchDoctor(ap.getDoctorId()).orElse(null) : null;
        Object patient = includeDetails ? service.fetchPatient(ap.getPatientId()).orElse(null) : null;
        return AppointmentMapper.toResponse(ap, doctor, patient);
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> create(@Valid @RequestBody AppointmentRequest req,
                                                      @RequestParam(defaultValue = "false") boolean includeDetails) {
        var saved = service.create(AppointmentMapper.toEntity(req));
        Object doctor = includeDetails ? service.fetchDoctor(saved.getDoctorId()).orElse(null) : null;
        Object patient = includeDetails ? service.fetchPatient(saved.getPatientId()).orElse(null) : null;
        return ResponseEntity.status(HttpStatus.CREATED).body(AppointmentMapper.toResponse(saved, doctor, patient));
    }

    @PutMapping("/{id}")
    public AppointmentResponse update(@PathVariable Long id,
                                      @Valid @RequestBody AppointmentRequest req,
                                      @RequestParam(defaultValue = "false") boolean includeDetails) {
        var updated = service.update(id, ap -> AppointmentMapper.update(ap, req));
        Object doctor = includeDetails ? service.fetchDoctor(updated.getDoctorId()).orElse(null) : null;
        Object patient = includeDetails ? service.fetchPatient(updated.getPatientId()).orElse(null) : null;
        return AppointmentMapper.toResponse(updated, doctor, patient);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) return Sort.by("date").descending();
        String[] parts = sort.split(",");
        String field = parts[0].trim();
        String dir = (parts.length > 1) ? parts[1].trim().toLowerCase() : "asc";
        return "desc".equals(dir) ? Sort.by(field).descending() : Sort.by(field).ascending();
    }
}
