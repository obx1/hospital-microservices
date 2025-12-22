package ma.you.hospital.patients.web;

import ma.you.hospital.patients.domain.Patient;
import ma.you.hospital.patients.web.dto.PatientRequest;
import ma.you.hospital.patients.web.dto.PatientResponse;

public final class PatientMapper {

    private PatientMapper() {}

    public static Patient toEntity(PatientRequest req) {
        Patient p = new Patient();
        p.setFirstName(req.firstName());
        p.setLastName(req.lastName());
        p.setGender(req.gender());
        p.setBirthDate(req.birthDate());
        p.setEmail(req.email());
        p.setPhone(req.phone());
        p.setAddress(req.address());
        return p;
    }

    public static void update(Patient p, PatientRequest req) {
        p.setFirstName(req.firstName());
        p.setLastName(req.lastName());
        p.setGender(req.gender());
        p.setBirthDate(req.birthDate());
        p.setEmail(req.email());
        p.setPhone(req.phone());
        p.setAddress(req.address());
    }

    public static PatientResponse toResponse(Patient p) {
        return new PatientResponse(
                p.getId(),
                p.getFirstName(),
                p.getLastName(),
                p.getGender(),
                p.getBirthDate(),
                p.getEmail(),
                p.getPhone(),
                p.getAddress(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}
