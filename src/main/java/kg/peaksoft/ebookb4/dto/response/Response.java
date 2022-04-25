package kg.peaksoft.ebookb4.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
public class Response {

    private HttpStatus httpStatus;
    private String message;
}
