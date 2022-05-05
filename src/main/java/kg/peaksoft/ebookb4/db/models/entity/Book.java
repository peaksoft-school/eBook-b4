package kg.peaksoft.ebookb4.db.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kg.peaksoft.ebookb4.aws.model.FileInfo;
import kg.peaksoft.ebookb4.db.models.booksClasses.Basket;
import kg.peaksoft.ebookb4.db.models.booksClasses.ClientOperations;
import kg.peaksoft.ebookb4.db.models.booksClasses.FileSources;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Language;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "hibernate_seq")
    @SequenceGenerator(name = "hibernate_seq",
            sequenceName = "book_seq", allocationSize = 1)
    @Column(name = "book_id")
    private Long bookId;
    private String title;
    private String authorFullName;
    private String aboutBook;
    private String publishingHouse;
    private LocalDate yearOfIssue;
    private Double price;
    private Boolean isBestSeller;
    @Enumerated(value = EnumType.STRING)
    private Language language;
    @Enumerated(value = EnumType.STRING)
    private BookType bookType;
    @Enumerated(value = EnumType.STRING)
    private RequestStatus requestStatus = RequestStatus.INPROGRESS;

    private Boolean adminWatch = false;

    private Boolean isNew = true;

    private int baskets;
    private int likes;
    private Integer discount;
    private LocalDate dateOfRegister;

    private Integer discountFromPromo;

    @OneToMany(cascade = {MERGE,REFRESH})
    private List<FileInfo> bookImage;


    @ManyToMany(cascade = CascadeType.REMOVE)
    @JsonIgnore
    @JoinTable(
            name = "books_basket"
            , joinColumns = @JoinColumn(name = "book_id")
            , inverseJoinColumns = @JoinColumn(name = "basket_id"))
    private List<Basket> basket;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JsonIgnore
    @JoinTable(
            name = "liked_books"
            , joinColumns = @JoinColumn(name = "book_id")
            , inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> likedBooks;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "operation_id")
    private ClientOperations operations;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

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

    @ManyToOne(fetch = FetchType.EAGER)
    private Genre genre;


    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title
                + ", price=" + price + "}";
    }
}