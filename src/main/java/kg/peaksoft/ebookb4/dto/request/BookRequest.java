package kg.peaksoft.ebookb4.dto.request;

import kg.peaksoft.ebookb4.db.models.bookClasses.AudioBook;
import kg.peaksoft.ebookb4.db.models.bookClasses.ElectronicBook;
import kg.peaksoft.ebookb4.db.models.bookClasses.PaperBook;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.enums.Language;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.logging.Filter;

@Getter
@Setter
public class BookRequest {

    private String title;
    private String authorFullName;
    private String aboutBook;
    private Integer discount;
    private Boolean isBestSeller;
    private LocalDate yearOfIssue;
    private Double price;
    private String publishingHouse;
    private Language language;
    private Genre genre;
    private BookType bookType;

    AudioBook audioBook;
    PaperBook paperBook;
    ElectronicBook electronicBook;
}
