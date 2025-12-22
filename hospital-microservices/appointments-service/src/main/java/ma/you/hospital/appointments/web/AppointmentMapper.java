package ma.you.hospital.appointments.web;

import ma.you.hospital.appointments.domain.*;
import ma.you.hospital.appointments.dto.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public final class AppointmentMapper {

    private AppointmentMapper() {}

    public static Appointment toEntity(AppointmentRequest req) {
        return Appointment.builder()
                .doctorId(req.getDoctorId())
                .patientId(req.getPatientId())
                .date(parseDate(req.getDate()))
                .time(parseTime(req.getTime()))
                .status(parseStatus(req.getStatus()))
                .build();
    }

    public static void update(Appointment ap, AppointmentRequest req) {
        ap.setDoctorId(req.getDoctorId());
        ap.setPatientId(req.getPatientId());
        ap.setDate(parseDate(req.getDate()));
        ap.setTime(parseTime(req.getTime()));
        ap.setStatus(parseStatus(req.getStatus()));
    }

    public static AppointmentResponse toResponse(Appointment ap, Object doctor, Object patient) {
        return AppointmentResponse.builder()
                .id(ap.getId())
                .doctorId(ap.getDoctorId())
                .patientId(ap.getPatientId())
                .date(ap.getDate() != null ? ap.getDate().toString() : null)
                .time(ap.getTime() != null ? ap.getTime().toString() : null)
                .status(ap.getStatus() != null ? ap.getStatus().name() : null)
                .doctor(doctor)
                .patient(patient)
                .build();
    }

    private static LocalDate parseDate(String s) {
        try {
            return LocalDate.parse(s);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format (expected yyyy-MM-dd)");
        }
    }

    private static LocalTime parseTime(String s) {
        try {
            return LocalTime.parse(s);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format (expected HH:mm or HH:mm:ss)");
        }
    }

    private static AppointmentStatus parseStatus(String s) {
        if (s == null || s.isBlank()) return AppointmentStatus.SCHEDULED;
        try {
            return AppointmentStatus.valueOf(s.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status. Allowed: SCHEDULED, CONFIRMED, CANCELLED, COMPLETED");
        }
    }
}
