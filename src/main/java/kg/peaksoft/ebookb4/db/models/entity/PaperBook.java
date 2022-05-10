package kg.peaksoft.ebookb4.db.models.entity;

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
public class PaperBook {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "hibernate_seq")
    @SequenceGenerator(name = "hibernate_seq",
            sequenceName = "paperbook_seq",
            allocationSize = 1)
    private Long paperBookId;

    private String fragmentOfBook;

    private Integer numberOfPages;

    private Integer numberOfSelected;

    private Integer numberOfSelectedCopy;

}
