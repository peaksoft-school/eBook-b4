package kg.peaksoft.ebookb4.db.models.response;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
public class VendorResponse {

    private Long vendorId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private int amountOfBooks;
    private LocalDate dateOfRegistration;
}
