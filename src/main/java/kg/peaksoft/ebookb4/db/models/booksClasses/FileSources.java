package kg.peaksoft.ebookb4.db.models.booksClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FileSources {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
    @SequenceGenerator(name = "hibernate_seq", sequenceName = "file_sources_seq", allocationSize = 1)
    private Long id;

    private String images;

    private String line1;

    private String line2;

    private String line3;

    private String line4;

}
