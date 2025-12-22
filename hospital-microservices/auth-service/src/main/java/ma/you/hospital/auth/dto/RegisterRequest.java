package ma.you.hospital.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    /**
     * Exemple : "ADMIN", "DOCTOR", "STAFF", "PATIENT"
     */
    @NotBlank
    private String role;
}
