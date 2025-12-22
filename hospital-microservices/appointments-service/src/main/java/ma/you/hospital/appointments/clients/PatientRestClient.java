package ma.you.hospital.appointments.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "PATIENTS-SERVICE")
public interface PatientRestClient {

    @GetMapping("/api/patients/{id}")
    Map<String, Object> getPatientById(@PathVariable Long id);
}
