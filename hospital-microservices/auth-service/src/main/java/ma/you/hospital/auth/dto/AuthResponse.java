package ma.you.hospital.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String accessToken;
    private String tokenType;
    private Long expiresIn;

    private Long userId;
    private String username;
    private String email;
    private String role;
}
