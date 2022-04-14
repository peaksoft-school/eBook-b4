package kg.peaksoft.ebookb4.dto.request;

import kg.peaksoft.ebookb4.models.enums.BookType;
import kg.peaksoft.ebookb4.models.enums.Genre;
import kg.peaksoft.ebookb4.models.enums.Language;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class ElectronicBookRequest {

//    private String images;

    private String title;
    private String authorFullName;
    private String aboutBook;
    private LocalDate yearOfIssue;
    private Integer discount;
    private BigDecimal price;
    private Boolean isBestSeller;
    private Language language;

    private BookType bookType;
    private Genre genre;


    private String publishingHouse;
    private String fragmentOfBook;
    private Integer numberOfPages;
    private String urlOfBookFromCloud;

}
