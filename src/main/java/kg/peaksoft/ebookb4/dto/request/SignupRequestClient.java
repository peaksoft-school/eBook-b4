package kg.peaksoft.ebookb4.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class SignupRequestClient {

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private String firstName;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
