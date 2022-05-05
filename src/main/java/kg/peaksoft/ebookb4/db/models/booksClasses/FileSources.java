package kg.peaksoft.ebookb4.db.models.booksClasses;

import kg.peaksoft.ebookb4.db.models.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileSources {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
    @SequenceGenerator(name = "hibernate_seq", sequenceName = "file_sources_seq", allocationSize = 1)
    private Long id;

    private String images;

    private String line1;
    private String line2;
    private String line3;
    private String line4;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
