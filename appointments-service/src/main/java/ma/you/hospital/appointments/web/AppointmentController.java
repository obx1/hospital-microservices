package ma.you.hospital.appointments.web;

import lombok.RequiredArgsConstructor;
import ma.you.hospital.appointments.dto.AppointmentRequest;
import ma.you.hospital.appointments.entities.Appointment;
import ma.you.hospital.appointments.services.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public Appointment create(@RequestBody AppointmentRequest request) {
        return appointmentService.createAppointment(request);
    }

    @GetMapping
    public List<Appointment> all() {
        return appointmentService.getAll();
    }
}
