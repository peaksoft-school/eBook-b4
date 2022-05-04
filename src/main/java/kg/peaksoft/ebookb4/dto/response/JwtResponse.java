package kg.peaksoft.ebookb4.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    String role;

    public JwtResponse(String accessToken, List<String> role) {
        this.token = accessToken;
        this.role = String.valueOf(role);
    }

}
