package kg.peaksoft.ebookb4.models.bookClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 13/4/22
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileSources {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "generator", allocationSize = 1)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
