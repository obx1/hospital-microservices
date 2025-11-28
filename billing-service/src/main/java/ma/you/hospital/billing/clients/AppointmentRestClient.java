package ma.you.hospital.billing.clients;

import ma.you.hospital.billing.dto.AppointmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "APPOINTMENTS-SERVICE")
public interface AppointmentRestClient {

    @GetMapping("/appointments/{id}")
    AppointmentDTO getAppointmentById(@PathVariable("id") Long id);
}
