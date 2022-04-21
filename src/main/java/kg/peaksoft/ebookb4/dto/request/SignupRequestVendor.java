package kg.peaksoft.ebookb4.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupRequestVendor {

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    @NotBlank @NumberFormat
    private String number;
}
