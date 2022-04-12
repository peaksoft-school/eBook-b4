package kg.peaksoft.ebookb4.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class AppFile {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "appFile_gen")
    @SequenceGenerator(
            name = "appFile_gen",
            sequenceName = "appFile_seq",
            allocationSize = 1)
    private Long imageId;

    private String imageName;

}
