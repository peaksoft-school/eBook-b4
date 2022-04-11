package kg.peaksoft.ebookb4.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import lombok.*;

@Entity
@Table(name = "books")
@Getter @Setter
@RequiredArgsConstructor
public class Book {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_gen")
    @SequenceGenerator(
            name = "book_seq",
            sequenceName = "book_seq",
            allocationSize = 1)
    private Long bookId;
    private String title;            // Называние книг
    private String authorFullName;   // Называние Автора
    private String publishingHouse;  // Издательство(Басмаканасы)
    private String language;         // Язык
    private LocalDate yearOfIssue;   // Год выпуска
    private Integer price;           // Цена
    private Integer discount;        // Скидка
    private boolean bestSeller;      // Мыкты сатуучу

    @OneToMany
    private Set<AppFile> images;     // Картинки

    @OneToOne
    private Genre genre;              // Жанры

    @OneToOne
    private AudioBook audioBook;

    @OneToOne
    private ElectronicBook electronicBook;

    @OneToOne
    private PaperBook paperBook;

}
