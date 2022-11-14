package kg.peaksoft.ebookb4.db.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private String token;

    private String type = "Bearer";

    private String firstName;

    private String role;

    public JwtResponse(String accessToken, List<String> role, String firstName) {
        this.token = accessToken;
        this.role = String.valueOf(role);
        this.firstName = firstName;
    }

}
