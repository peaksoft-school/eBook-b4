package kg.peaksoft.ebookb4.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class PaperBook {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "paperBook_gen")
    @SequenceGenerator(
            name = "paperBook_gen",
            sequenceName = "paperBook_seq",
            allocationSize = 1)
    private Long paperBookId;

    private Integer amount;          // количество

    @OneToOne
    private AppFile aboutBook;

    @OneToOne
    private AppFile fragment;

    @OneToOne
    private AppFile book;

}
