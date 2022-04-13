package kg.peaksoft.ebookb4.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter @Setter
public class SignupRequest {

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  private String firstName;

  private String lastName;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

  private String number;
}
