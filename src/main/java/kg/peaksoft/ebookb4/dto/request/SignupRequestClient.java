package kg.peaksoft.ebookb4.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class SignupRequestClient {

    private String email;

    private String firstName;
    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;
}
