package kg.peaksoft.ebookb4.dto.response;

import kg.peaksoft.ebookb4.db.models.books.Book;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class VendorResponse {

    private Long vendorId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private List<Book> vendorAddedBooks;

}
