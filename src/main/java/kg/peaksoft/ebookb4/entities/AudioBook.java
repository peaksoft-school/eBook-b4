package kg.peaksoft.ebookb4.entities;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class AudioBook {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "audioBook_gen")
    @SequenceGenerator(
            name = "audioBook_gen",
            sequenceName = "audioBook_seq",
            allocationSize = 1)
    private Long audioBookId;

    private LocalDate duration;

    @OneToOne
    private AppFile aboutBook;

    @OneToOne
    private AppFile book;



}
