package kg.peaksoft.ebookb4.dto.request;

import kg.peaksoft.ebookb4.models.enums.Genre;
import kg.peaksoft.ebookb4.models.enums.Language;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class eBookRequest {

    String images;

    private String title;
    private String authorFullName;

    private Genre genre;

    private String publishingHouse;

    private String aboutBook;

    private String fragmentOfBook;

    private Language language;

    private LocalDate yearOfIssue;

    private Integer numberOfPages;

    private Boolean isBestSeller;

    private BigDecimal price;

    private Integer discount;

}
