package kg.peaksoft.ebookb4.db.models.response;

import kg.peaksoft.ebookb4.db.models.booksClasses.FileInformation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {

    private Long bookId;
    private String title;
    private String authorFullName;
    private String aboutBook;
    private String publishingHouse;
    private LocalDate dateOfRegister;
    private Double price;
    private Boolean adminWatch;

    private FileInformation fileInformation;

}
