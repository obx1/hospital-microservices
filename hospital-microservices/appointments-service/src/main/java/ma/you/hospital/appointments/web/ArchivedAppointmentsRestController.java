package ma.you.hospital.appointments.web;

import lombok.RequiredArgsConstructor;
import ma.you.hospital.appointments.domain.ArchivedAppointment;
import ma.you.hospital.appointments.repositories.ArchivedAppointmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/archived-appointments")
public class ArchivedAppointmentsRestController {

    private final ArchivedAppointmentRepository archivedAppointmentRepository;

    // GET /archived-appointments  → liste complète
    @GetMapping
    public List<ArchivedAppointment> getAllArchivedAppointments() {
        return archivedAppointmentRepository.findAll();
    }

    // GET /archived-appointments/{id}  → une archive par id
    @GetMapping("/{id}")
    public ResponseEntity<ArchivedAppointment> getArchivedAppointmentById(@PathVariable Long id) {
        return archivedAppointmentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
