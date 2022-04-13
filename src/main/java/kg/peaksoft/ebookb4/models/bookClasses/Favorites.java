package kg.peaksoft.ebookb4.models.bookClasses;

import kg.peaksoft.ebookb4.models.userClasses.User;

import javax.persistence.*;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
@Entity
public class Favorites {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "generator", allocationSize = 1)
    private Long favoriteId;

    @OneToOne
    private Book book;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User User;


}
