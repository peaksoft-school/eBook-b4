package kg.peaksoft.ebookb4.dto.response;

import kg.peaksoft.ebookb4.db.models.books.Book;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
public class VendorResponse {

    private Long vendorId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private int amountOfBooks;

//    private LocalDate dateOfRegistration;
}
