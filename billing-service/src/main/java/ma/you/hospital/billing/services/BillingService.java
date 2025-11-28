package ma.you.hospital.billing.services;

import lombok.RequiredArgsConstructor;
import ma.you.hospital.billing.entities.Bill;
import ma.you.hospital.billing.model.AppointmentDTO;
import ma.you.hospital.billing.model.DoctorDTO;
import ma.you.hospital.billing.model.PatientDTO;
import ma.you.hospital.billing.openfeign.AppointmentRestClient;
import ma.you.hospital.billing.openfeign.DoctorRestClient;
import ma.you.hospital.billing.openfeign.PatientRestClient;
import ma.you.hospital.billing.repositories.BillRepository;
import ma.you.hospital.billing.web.dto.BillRequestDTO;
import ma.you.hospital.billing.web.dto.BillResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillRepository billRepository;
    private final DoctorRestClient doctorRestClient;
    private final PatientRestClient patientRestClient;
    private final AppointmentRestClient appointmentRestClient;

    public BillResponseDTO createBill(BillRequestDTO request) {

        // Récupérer le rendez-vous
        AppointmentDTO appointment = appointmentRestClient.getAppointmentById(request.getAppointmentId());

        // Récupérer patient + docteur
        DoctorDTO doctor = doctorRestClient.getDoctorById(appointment.getDoctorId());
        PatientDTO patient = patientRestClient.getPatientById(appointment.getPatientId());

        Bill bill = Bill.builder()
                .appointmentId(appointment.getId())
                .doctorId(appointment.getDoctorId())
                .patientId(appointment.getPatientId())
                .amount(request.getAmount())
                .billDate(LocalDate.now())
                .description(request.getDescription())
                .build();

        Bill saved = billRepository.save(bill);

        return BillResponseDTO.builder()
                .bill(saved)
                .doctor(doctor)
                .patient(patient)
                .build();
    }

    public List<BillResponseDTO> getAllBills() {
        return billRepository.findAll().stream().map(bill -> {
            DoctorDTO doctor = doctorRestClient.getDoctorById(bill.getDoctorId());
            PatientDTO patient = patientRestClient.getPatientById(bill.getPatientId());

            return BillResponseDTO.builder()
                    .bill(bill)
                    .doctor(doctor)
                    .patient(patient)
                    .build();
        }).collect(Collectors.toList());
    }

    public BillResponseDTO getBillById(Long id) {
        Bill bill = billRepository.findById(id).orElseThrow();
        DoctorDTO doctor = doctorRestClient.getDoctorById(bill.getDoctorId());
        PatientDTO patient = patientRestClient.getPatientById(bill.getPatientId());

        return BillResponseDTO.builder()
                .bill(bill)
                .doctor(doctor)
                .patient(patient)
                .build();
    }
}
