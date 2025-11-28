package ma.you.hospital.appointments.services;

import lombok.RequiredArgsConstructor;
import ma.you.hospital.appointments.clients.DoctorRestClient;
import ma.you.hospital.appointments.clients.PatientRestClient;
import ma.you.hospital.appointments.dto.AppointmentRequest;
import ma.you.hospital.appointments.domain.Appointment;
import ma.you.hospital.appointments.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRestClient doctorRestClient;
    private final PatientRestClient patientRestClient;

    public Appointment createAppointment(AppointmentRequest request) {

        // Vérifier patient
        patientRestClient.getPatientById(request.getPatientId());

        // Vérifier docteur
        doctorRestClient.getDoctorById(request.getDoctorId());

        // Créer rdv
        Appointment ap = Appointment.builder()
                .doctorId(request.getDoctorId())
                .patientId(request.getPatientId())
                .date(request.getDate())
                .time(request.getTime())
                .status("CONFIRMED")
                .build();

        return appointmentRepository.save(ap);
    }

    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    // ✅ NOUVELLE MÉTHODE UTILISÉE PAR LE CONTROLLER ET PAR BILLING-SERVICE
    public Appointment getById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Appointment not found with id " + id
                        )
                );
    }
}
