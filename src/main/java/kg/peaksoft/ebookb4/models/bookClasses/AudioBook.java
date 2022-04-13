package kg.peaksoft.ebookb4.models.bookClasses;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
@Entity
@Getter
@Setter
public class AudioBook {



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "generator", allocationSize = 1)
    private Long audioBookId;

    private LocalDate duration;

    private String fragment;

    private String audioBook;

    private String urlOfBookFromCloud;
}
