package ma.you.hospital.auth.config;

import lombok.extern.slf4j.Slf4j;
import ma.you.hospital.auth.domain.Role;
import ma.you.hospital.auth.domain.User;
import ma.you.hospital.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository,
                                       PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                log.info("Creating default admin user...");

                User admin = User.builder()
                        .username("admin")
                        .email("admin@hospital.local")
                        .password(passwordEncoder.encode("admin123"))
                        .role(Role.ADMIN)
                        .enabled(true)
                        .build();

                userRepository.save(admin);

                log.info("Default admin created: username=admin / password=admin123");
            }
        };
    }
}
