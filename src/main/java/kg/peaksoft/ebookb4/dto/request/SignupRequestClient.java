package kg.peaksoft.ebookb4.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupRequestClient {

    @Email
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String password;
}
