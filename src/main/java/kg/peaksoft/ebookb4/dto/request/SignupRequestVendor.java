package kg.peaksoft.ebookb4.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

  @JsonIgnore
  private String password;

  private String confirmPassword;


}
