package kg.peaksoft.ebookb4.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {

    @Email
    private String email;
    @NotBlank
    private String password;

}
