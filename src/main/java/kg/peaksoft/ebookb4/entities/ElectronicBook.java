package kg.peaksoft.ebookb4.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class ElectronicBook {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "electronicBook_seq")
    @SequenceGenerator(
            name = "electronicBook_gen",
            sequenceName = "electronicBook_seq",
            allocationSize = 1)
    private Long ElectronicBookId;

    @OneToOne
    private AppFile aboutBook;

    @OneToOne
    private AppFile fragment;

    @OneToOne
    private AppFile book;


}
