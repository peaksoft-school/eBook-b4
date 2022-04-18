package kg.peaksoft.ebookb4.db.models.others;

import kg.peaksoft.ebookb4.db.models.bookClasses.Book;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import lombok.*;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
@Entity
@Getter @Setter
@RequiredArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "generator", allocationSize = 1)
    @Column(name = "basket_id")
    private Long basketId;

    @OneToMany
    @JoinTable(
            name = "basket_books",
            joinColumns = @JoinColumn(
                    name = "basket_id",
                    referencedColumnName = "basket_id"

            ),
            inverseJoinColumns = @JoinColumn(
                    name = "book_id",
                    referencedColumnName = "book_id"
            )
    )
    private List<Book> books;


}
