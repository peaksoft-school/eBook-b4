package kg.peaksoft.ebookb4.dto.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class ClientDTO {

    private String email;

    private String firstName;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
}
