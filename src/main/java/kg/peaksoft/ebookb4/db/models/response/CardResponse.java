package kg.peaksoft.ebookb4.db.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private int yearOfIssue;

    private Double price;

    private Double sumAfterDiscount;

    private Integer promoDiscount;

    int countOfBooksInTotal;

    Integer discount;

    int countOfPaperBook = 0;

}
