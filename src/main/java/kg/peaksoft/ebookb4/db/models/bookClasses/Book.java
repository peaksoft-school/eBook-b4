package kg.peaksoft.ebookb4.db.models.bookClasses;

import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.enums.Language;
import kg.peaksoft.ebookb4.db.models.others.FileSources;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter @Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
    @SequenceGenerator(name = "hibernate_seq", sequenceName = "book_seq", allocationSize = 1)
    @Column(name = "book_id")
    private Long bookId;
    private String title;
    private String authorFullName;
    private String aboutBook;

    private LocalDate yearOfIssue;

    private Integer discount;
    private BigDecimal price;
    private Boolean isBestSeller;
    @Enumerated(value = EnumType.STRING)
    private Language language;
    @Enumerated(value = EnumType.STRING)
    private BookType bookType;
    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    private int baskets;
    private int likes;



    @OneToMany(mappedBy = "book")
    private List<FileSources> images;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "audiobook_id")
    private AudioBook audioBook;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ebook_id")
    private ElectronicBook electronicBook;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paperbook_id")
    private PaperBook paperBook;

}