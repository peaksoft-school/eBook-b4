package kg.peaksoft.ebookb4.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class SignupRequestVendor {

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  private String firstName;

  private String lastName;

  private String number;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

  private String newPassword;

  private String confirmPassword;


}
