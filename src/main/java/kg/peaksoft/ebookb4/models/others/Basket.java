package kg.peaksoft.ebookb4.models.others;

import kg.peaksoft.ebookb4.models.bookClasses.Book;
import kg.peaksoft.ebookb4.models.userClasses.User;
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

    @ManyToMany
    private List<Book> books;

    @OneToOne
    private User user;
}
