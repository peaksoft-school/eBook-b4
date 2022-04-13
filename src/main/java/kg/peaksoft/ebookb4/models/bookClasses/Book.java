package kg.peaksoft.ebookb4.models.bookClasses;

import kg.peaksoft.ebookb4.models.enums.BookType;
import kg.peaksoft.ebookb4.models.enums.Genre;
import kg.peaksoft.ebookb4.models.enums.Language;
import kg.peaksoft.ebookb4.models.userClasses.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "generator", allocationSize = 1)
    private Long bookId;
    private String title;
    private String authorFullName;
    private LocalDate yearOfIssue;
    private BigDecimal price;
    private Integer discount;
    private Boolean isBestSeller;
    private Language language;
    private BookType bookType;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<FileSources> images;
    private Genre genre;
    private Integer likes;

    @OneToOne
    private User user;

    private Integer busket;
    @OneToOne
    private AudioBook audioBook;
    @OneToOne
    private ElectronicBook electronicBook;
    @OneToOne
    private PaperBook paperBook;
}
