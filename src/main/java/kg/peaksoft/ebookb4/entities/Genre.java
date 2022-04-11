package kg.peaksoft.ebookb4.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "genre_gen")
    @SequenceGenerator(
            name = "genre_seq",
            sequenceName = "genre_seq",
            allocationSize = 1)
    private Long id;

    private String genreName;


}
