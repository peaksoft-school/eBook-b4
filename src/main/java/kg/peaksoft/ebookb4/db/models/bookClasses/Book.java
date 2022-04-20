package kg.peaksoft.ebookb4.db.models.bookClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.enums.Language;
import kg.peaksoft.ebookb4.db.models.others.Basket;
import kg.peaksoft.ebookb4.db.models.others.ClientOperations;
import kg.peaksoft.ebookb4.db.models.others.FileSources;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
    @SequenceGenerator(name = "hibernate_seq", sequenceName = "book_seq", allocationSize = 1)
    @Column(name = "book_id")
    private Long bookId;
    private String title;
    private String authorFullName;
    private String aboutBook;
    private String publishingHouse;

    private LocalDate yearOfIssue;

    private Double price;
    private Boolean isBestSeller;
    private int baskets;
    private int likes;
    private Boolean isActive = false;
    private Integer discount;
    private Integer discountFromPromo;

    @Enumerated(value = EnumType.STRING)
    private Language language;
    @Enumerated(value = EnumType.STRING)
    private BookType bookType;
    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "books_basket"
            ,joinColumns = @JoinColumn(name = "book_id")
            ,inverseJoinColumns = @JoinColumn(name = "basket_id"))
    private List<Basket> basket;


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