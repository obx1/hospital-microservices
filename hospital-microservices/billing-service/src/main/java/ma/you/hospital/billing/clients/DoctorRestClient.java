package ma.you.hospital.billing.clients;

import ma.you.hospital.billing.dto.DoctorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "DOCTORS-SERVICE")
public interface DoctorRestClient {

    @GetMapping("/api/doctors/{id}")
    DoctorDTO getDoctorById(@PathVariable("id") Long id);
}
