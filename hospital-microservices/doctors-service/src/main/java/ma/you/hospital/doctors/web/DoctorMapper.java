package ma.you.hospital.doctors.web;

import ma.you.hospital.doctors.domain.Doctor;
import ma.you.hospital.doctors.dto.DoctorRequest;
import ma.you.hospital.doctors.dto.DoctorResponse;

public final class DoctorMapper {

    private DoctorMapper() {}

    public static Doctor toEntity(DoctorRequest req) {
        Doctor d = new Doctor();
        d.setFirstName(req.firstName());
        d.setLastName(req.lastName());
        d.setSpecialty(req.specialty());
        d.setEmail(req.email());
        d.setPhone(req.phone());
        d.setAddress(req.address());
        return d;
    }

    public static void update(Doctor d, DoctorRequest req) {
        d.setFirstName(req.firstName());
        d.setLastName(req.lastName());
        d.setSpecialty(req.specialty());
        d.setEmail(req.email());
        d.setPhone(req.phone());
        d.setAddress(req.address());
    }

    public static DoctorResponse toResponse(Doctor d) {
        return new DoctorResponse(
                d.getId(),
                d.getFirstName(),
                d.getLastName(),
                d.getSpecialty(),
                d.getEmail(),
                d.getPhone(),
                d.getAddress(),
                d.getCreatedAt(),
                d.getUpdatedAt()
        );
    }
}
