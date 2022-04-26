package kg.peaksoft.ebookb4.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 25/4/22
 */
@Getter @Setter
@AllArgsConstructor
public class BookResponse {

    private Long bookId;
    private String title;
    private String authorFullName;
    private String aboutBook;
    private String publishingHouse;
    private LocalDate yearOfIssue;
    private Double price;

}