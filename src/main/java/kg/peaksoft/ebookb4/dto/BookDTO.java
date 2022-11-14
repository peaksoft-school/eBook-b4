package kg.peaksoft.ebookb4.dto;

import kg.peaksoft.ebookb4.db.models.booksClasses.FileInformation;
import kg.peaksoft.ebookb4.db.models.entity.AudioBook;
import kg.peaksoft.ebookb4.db.models.entity.ElectronicBook;
import kg.peaksoft.ebookb4.db.models.entity.PaperBook;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Language;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {

    private String title;

    private String authorFullName;

    private String aboutBook;

    private int yearOfIssue;

    private Double price;

    private String publishingHouse;

    private Integer discount;

    private Boolean isBestSeller;

    private Language language;

    private Long genreId;

    private BookType bookType;

    AudioBook audioBook;

    PaperBook paperBook;

    ElectronicBook electronicBook;

    FileInformation fileInformation;

}
