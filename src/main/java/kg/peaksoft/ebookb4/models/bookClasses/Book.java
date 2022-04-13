package kg.peaksoft.ebookb4.models.bookClasses;

import com.fasterxml.jackson.annotation.JsonFormat;
import kg.peaksoft.ebookb4.models.enums.BookType;
import kg.peaksoft.ebookb4.models.enums.Genre;
import kg.peaksoft.ebookb4.models.enums.Language;
import kg.peaksoft.ebookb4.models.others.FileSources;
import kg.peaksoft.ebookb4.models.userClasses.User;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
@Entity
@RequiredArgsConstructor
@Getter @Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "generator", allocationSize = 1)
    private Long bookId;
    private String title;
    private String authorFullName;

    private String aboutBook;
    @JsonFormat(pattern = "yyyy")
    private LocalDate yearOfIssue;
    private Integer discount;
    private BigDecimal price;
    private Boolean isBestSeller;

    private int baskets;
    private int likes;

    private Language language;
    private BookType bookType;
    private Genre genre;


    @OneToMany(mappedBy = "book")
    private List<FileSources> images;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "audiobook_id")
    private AudioBook audioBook;

    @OneToOne
    @JoinColumn(name = "ebook_id")
    private ElectronicBook electronicBook;

    @OneToOne
    @JoinColumn(name = "paperbook_id")
    private PaperBook paperBook;
}