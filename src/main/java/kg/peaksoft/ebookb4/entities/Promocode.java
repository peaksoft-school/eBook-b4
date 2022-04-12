package kg.peaksoft.ebookb4.entities;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "promocods")

public class Promocode {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "gen")
    @SequenceGenerator(
            name = "gen",
            sequenceName = "seq",
            allocationSize = 1)
    private Long id;
    private String name;
    private LocalDate dateOfStart;
    private LocalDate dateOfFinish;


}
