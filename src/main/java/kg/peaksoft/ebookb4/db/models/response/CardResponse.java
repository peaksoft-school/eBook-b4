package kg.peaksoft.ebookb4.db.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private Double sumAfterDiscount;
    private Integer promoDiscount;
    int countOfBooksInTotal;
    Integer discount;
//    Double sum;
//    Double total;

    int countOfPaperBook = 0;

}
