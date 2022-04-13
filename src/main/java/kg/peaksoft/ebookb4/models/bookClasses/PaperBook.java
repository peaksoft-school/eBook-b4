package kg.peaksoft.ebookb4.models.bookClasses;

import javax.persistence.*;
import java.io.File;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */

@Entity
public class PaperBook {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "generator", allocationSize = 1)
    private Long paperBookId;
    private String publishingHouse;
//    private File aboutBook;
//    private File fragmentOfBook;


    private Integer numberOfPages;
    private Integer numberOfSelected;

}
