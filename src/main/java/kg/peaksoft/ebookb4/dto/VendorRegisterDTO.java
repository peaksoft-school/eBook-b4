package kg.peaksoft.ebookb4.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class VendorRegisterDTO {

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private String firstName;

    private String lastName;

    private String number;

    private String password;

    private String confirmPassword;

}
