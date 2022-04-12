package kg.peaksoft.ebookb4.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "promocodes")
public class Promocode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String name;
    private LocalDate dateOfStart;
    private LocalDate dateOfFinish;



}
