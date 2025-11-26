package ma.you.hospital.doctors.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HelloController {
    record HelloResponse(String msg) {}
    @GetMapping("/hello")
    HelloResponse hello() { return new HelloResponse("ok"); }
}
