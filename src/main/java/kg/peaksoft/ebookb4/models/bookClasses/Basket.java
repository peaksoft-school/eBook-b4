package kg.peaksoft.ebookb4.models.bookClasses;

import kg.peaksoft.ebookb4.models.userClasses.User;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
@Entity
@RequiredArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "generator", allocationSize = 1)
    private Long basketId;
    @OneToOne
    private Book book;
    @OneToOne
    private User user;
}
