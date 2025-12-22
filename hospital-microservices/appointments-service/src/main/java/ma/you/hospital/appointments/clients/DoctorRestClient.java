package ma.you.hospital.appointments.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "DOCTORS-SERVICE")
public interface DoctorRestClient {

    @GetMapping("/api/doctors/{id}")
    Map<String, Object> getDoctorById(@PathVariable Long id);
}
