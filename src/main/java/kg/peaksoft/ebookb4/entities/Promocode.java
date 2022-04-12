package kg.peaksoft.ebookb4.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "promocods")
@Getter
@Setter
@RequiredArgsConstructor
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
