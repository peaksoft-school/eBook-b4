package kg.peaksoft.ebookb4.exceptions.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter @Setter
public class ExceptionResponse {
    private HttpStatus status;
    private String message;
}
