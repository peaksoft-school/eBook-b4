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
public class PlaceCounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    Long id;

    private Double discountPB;
    private Double sumPB;
    private Double totalPB;
    private int countOfPaperBookPB;
    private double sumAfterPromoPB;

    private int countOfBooksInTotal;
    private Double discount;
    private Double sum;
    private Double total;
    private Double sumAfterPromo;

}
