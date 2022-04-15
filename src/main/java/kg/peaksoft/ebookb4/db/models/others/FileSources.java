package kg.peaksoft.ebookb4.db.models.others;

import kg.peaksoft.ebookb4.db.models.bookClasses.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileSources {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "generator", allocationSize = 1)
    private Long id;

    private String images;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
