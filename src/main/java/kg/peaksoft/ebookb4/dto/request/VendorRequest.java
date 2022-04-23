package kg.peaksoft.ebookb4.dto.request;


import kg.peaksoft.ebookb4.db.models.books.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private List<Book> vendorAddedBooks = new ArrayList<>();
}
