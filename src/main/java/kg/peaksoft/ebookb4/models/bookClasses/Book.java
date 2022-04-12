package kg.peaksoft.ebookb4.models.bookClasses;

import kg.peaksoft.ebookb4.models.enums.BookType;
import kg.peaksoft.ebookb4.models.enums.Genre;
import kg.peaksoft.ebookb4.models.enums.Language;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
public class Book {
    private Long bookId;
    private String title;
    private String authorFullName;
    private LocalDate yearOfIssue;
    private BigDecimal price;
    private Integer discount;
    private Boolean isBestSeller;
    private Language language;
    private BookType bookType;

    // Тут будут картинки или файлы


    private Genre genre;

    private Integer likes;

    private Integer busket;

    private AudioBook audioBook;

    private ElectronicBook electronicBook;

    private PaperBook paperBook;
}
