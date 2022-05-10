package kg.peaksoft.ebookb4.db.models.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardResponse {
    private Long bookId;
    private String title;
    private String authorFullName;
    private String aboutBook;
    private String publishingHouse;
    private LocalDate yearOfIssue;
    private Double price;

    int countOfBooksInTotal;
    Double discount;
    Double sum;
    Double total;

    int countOfPaperBook = 0;

}
