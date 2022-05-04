package kg.peaksoft.ebookb4.db.models.books;

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
public class ElectronicBook {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
    @SequenceGenerator(name = "hibernate_seq", sequenceName = "ebook_seq", allocationSize = 1)
    private Long ebookId;

    private String fragmentOfBook;

    private Integer numberOfPages;

    private String urlOfBookFromCloud;


}
