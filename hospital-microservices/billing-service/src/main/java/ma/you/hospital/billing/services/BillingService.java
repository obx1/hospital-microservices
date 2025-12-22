package ma.you.hospital.billing.services;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import ma.you.hospital.billing.domain.Bill;
import ma.you.hospital.billing.dto.AppointmentDTO;
import ma.you.hospital.billing.dto.DoctorDTO;
import ma.you.hospital.billing.dto.PatientDTO;
import ma.you.hospital.billing.clients.AppointmentRestClient;
import ma.you.hospital.billing.clients.DoctorRestClient;
import ma.you.hospital.billing.clients.PatientRestClient;
import ma.you.hospital.billing.repositories.BillRepository;
import ma.you.hospital.billing.web.dto.BillRequestDTO;
import ma.you.hospital.billing.web.dto.BillResponseDTO;
import org.springframework.data.domain.*;
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

    // -----------------------
    // Création de facture
    // -----------------------
    public BillResponseDTO createBill(BillRequestDTO request) {

        if (billRepository.existsByAppointmentId(request.getAppointmentId())) {
            throw new BusinessException("Bill already exists for appointment " + request.getAppointmentId());
        }

        // 1) Récupérer le rendez-vous
        AppointmentDTO appointment = fetchAppointmentOrThrow(request.getAppointmentId());

        // 2) Récupérer patient + docteur
        DoctorDTO doctor = fetchDoctorOrThrow(appointment.getDoctorId());
        PatientDTO patient = fetchPatientOrThrow(appointment.getPatientId());

        // 3) Créer la facture
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

    // -----------------------
    // Lecture : toutes les factures (sans pagination) - comme avant
    // -----------------------
    @CircuitBreaker(name = "billingService", fallbackMethod = "getAllBillsFallback")
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

    private List<BillResponseDTO> getAllBillsFallback(Throwable ex) {
        return billRepository.findAll().stream()
                .map(bill -> BillResponseDTO.builder()
                        .bill(bill)
                        .doctor(null)
                        .patient(null)
                        .build())
                .collect(Collectors.toList());
    }

    // -----------------------
    // Lecture : facture par id
    // -----------------------
    @CircuitBreaker(name = "billingService", fallbackMethod = "getBillByIdFallback")
    public BillResponseDTO getBillById(Long id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id " + id));

        DoctorDTO doctor = doctorRestClient.getDoctorById(bill.getDoctorId());
        PatientDTO patient = patientRestClient.getPatientById(bill.getPatientId());

        return BillResponseDTO.builder()
                .bill(bill)
                .doctor(doctor)
                .patient(patient)
                .build();
    }

    private BillResponseDTO getBillByIdFallback(Long id, Throwable ex) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id " + id));

        return BillResponseDTO.builder()
                .bill(bill)
                .doctor(null)
                .patient(null)
                .build();
    }

    // -----------------------
    // Search / pagination (CRUD complet)
    // -----------------------
    @CircuitBreaker(name = "billingService", fallbackMethod = "searchBillsFallback")
    public Page<BillResponseDTO> searchBills(Long patientId, Long doctorId, Long appointmentId, Pageable pageable) {
        return billRepository.search(patientId, doctorId, appointmentId, pageable)
                .map(bill -> {
                    DoctorDTO doctor = doctorRestClient.getDoctorById(bill.getDoctorId());
                    PatientDTO patient = patientRestClient.getPatientById(bill.getPatientId());
                    return BillResponseDTO.builder().bill(bill).doctor(doctor).patient(patient).build();
                });
    }

    private Page<BillResponseDTO> searchBillsFallback(Long patientId, Long doctorId, Long appointmentId, Pageable pageable, Throwable ex) {
        return billRepository.search(patientId, doctorId, appointmentId, pageable)
                .map(bill -> BillResponseDTO.builder().bill(bill).doctor(null).patient(null).build());
    }

    // -----------------------
    // Update
    // -----------------------
    public BillResponseDTO updateBill(Long id, BillRequestDTO request) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with id " + id));

        bill.setAmount(request.getAmount());
        bill.setDescription(request.getDescription());

        Bill saved = billRepository.save(bill);

        // détails externes (best effort)
        DoctorDTO doctor = safeDoctor(saved.getDoctorId());
        PatientDTO patient = safePatient(saved.getPatientId());

        return BillResponseDTO.builder().bill(saved).doctor(doctor).patient(patient).build();
    }

    // -----------------------
    // Delete
    // -----------------------
    public void deleteBill(Long id) {
        if (!billRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bill not found with id " + id);
        }
        billRepository.deleteById(id);
    }

    // -----------------------
    // Helpers Feign (erreurs propres)
    // -----------------------
    private AppointmentDTO fetchAppointmentOrThrow(Long appointmentId) {
        try {
            return appointmentRestClient.getAppointmentById(appointmentId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Appointment not found with id " + appointmentId);
        } catch (FeignException e) {
            throw new BusinessException("Appointments-service unavailable");
        }
    }

    private DoctorDTO fetchDoctorOrThrow(Long doctorId) {
        try {
            return doctorRestClient.getDoctorById(doctorId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Doctor not found with id " + doctorId);
        } catch (FeignException e) {
            throw new BusinessException("Doctors-service unavailable");
        }
    }

    private PatientDTO fetchPatientOrThrow(Long patientId) {
        try {
            return patientRestClient.getPatientById(patientId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Patient not found with id " + patientId);
        } catch (FeignException e) {
            throw new BusinessException("Patients-service unavailable");
        }
    }

    private DoctorDTO safeDoctor(Long doctorId) {
        try { return doctorRestClient.getDoctorById(doctorId); }
        catch (Exception e) { return null; }
    }

    private PatientDTO safePatient(Long patientId) {
        try { return patientRestClient.getPatientById(patientId); }
        catch (Exception e) { return null; }
    }
}
