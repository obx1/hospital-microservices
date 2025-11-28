package ma.you.hospital.billing.openfeign;

import ma.you.hospital.billing.model.AppointmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "APPOINTMENTS-SERVICE")
public interface AppointmentRestClient {

    @GetMapping("/appointments/{id}")
    AppointmentDTO getAppointmentById(@PathVariable("id") Long id);
}
