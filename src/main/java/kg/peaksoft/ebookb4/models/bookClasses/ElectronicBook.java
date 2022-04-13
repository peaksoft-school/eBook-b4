package kg.peaksoft.ebookb4.models.bookClasses;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
@Entity
@Getter
@Setter
public class ElectronicBook {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "generator", allocationSize = 1)
    private Long ebookId;
    private String publishingHouse;
    private String aboutBook;
    private String fragmentOfBook;
    private Integer numberOfPages;
    private String urlOfBookFromCloud;


}
