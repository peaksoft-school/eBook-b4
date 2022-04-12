package kg.peaksoft.ebookb4.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class UserResponse {
    private String email;
    private String password;
}
