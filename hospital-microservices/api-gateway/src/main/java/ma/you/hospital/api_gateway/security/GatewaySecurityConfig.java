package ma.you.hospital.api_gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class GatewaySecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex

                        // 🔓 AUTH SERVICE (PUBLIC)
                        .pathMatchers("/api/auth/**").permitAll()

                        // 🔓 ACTUATOR
                        .pathMatchers("/actuator/**").permitAll()

                        // 🔒 TOUT LE RESTE PASSE (sera sécurisé par les microservices)
                        .anyExchange().permitAll()
                )
                .build();
    }
}
