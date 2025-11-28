package ma.you.hospital.billing.openfeign;

import ma.you.hospital.billing.model.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PATIENTS-SERVICE")
public interface PatientRestClient {

    @GetMapping("/api/patients/{id}")
    PatientDTO getPatientById(@PathVariable("id") Long id);
}
