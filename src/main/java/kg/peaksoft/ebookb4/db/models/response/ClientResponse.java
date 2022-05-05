package kg.peaksoft.ebookb4.db.models.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClientResponse {

    private Long id;
    private String name;
    public String email;
    private LocalDate dateOfRegistration;
}
