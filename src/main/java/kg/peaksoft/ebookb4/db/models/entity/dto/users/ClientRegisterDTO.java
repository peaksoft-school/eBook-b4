package kg.peaksoft.ebookb4.db.models.entity.dto.users;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class ClientRegisterDTO {

    private String email;

    private String firstName;
    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;
}
