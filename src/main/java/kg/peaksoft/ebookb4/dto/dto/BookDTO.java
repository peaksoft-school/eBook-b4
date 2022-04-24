package kg.peaksoft.ebookb4.dto.dto;

import kg.peaksoft.ebookb4.db.models.books.AudioBook;
import kg.peaksoft.ebookb4.db.models.books.ElectronicBook;
import kg.peaksoft.ebookb4.db.models.books.PaperBook;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.enums.Language;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class BookDTO {

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
