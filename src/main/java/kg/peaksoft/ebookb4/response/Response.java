package kg.peaksoft.ebookb4.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
@Builder
@Getter
@Setter
public class Response {
    private HttpStatus httpStatus;

    private String message;
}
