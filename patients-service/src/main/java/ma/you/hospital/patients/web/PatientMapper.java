package ma.you.hospital.patients.web;

import ma.you.hospital.patients.domain.Patient;
import ma.you.hospital.patients.web.dto.*;

public class PatientMapper {

    public static Patient toEntity(PatientRequest r) {
        var p = new Patient();
        p.setFirstName(r.firstName());
        p.setLastName(r.lastName());
        p.setGender(r.gender());
        p.setBirthDate(r.birthDate());
        p.setEmail(r.email());
        p.setPhone(r.phone());
        p.setAddress(r.address());
        return p;
    }

    public static void update(Patient p, PatientRequest r) {
        p.setFirstName(r.firstName());
        p.setLastName(r.lastName());
        p.setGender(r.gender());
        p.setBirthDate(r.birthDate());
        p.setEmail(r.email());
        p.setPhone(r.phone());
        p.setAddress(r.address());
    }

    public static PatientResponse toResponse(Patient p) {
        return new PatientResponse(
                p.getId(), p.getFirstName(), p.getLastName(), p.getGender(),
                p.getBirthDate(), p.getEmail(), p.getPhone(), p.getAddress(),
                p.getCreatedAt(), p.getUpdatedAt()
        );
    }
}
