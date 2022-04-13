package kg.peaksoft.ebookb4.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class LoginRequest {
	@NotBlank
  	private String email;

	@NotBlank
	private String password;

}
