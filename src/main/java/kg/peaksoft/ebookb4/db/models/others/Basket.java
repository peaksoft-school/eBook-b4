package kg.peaksoft.ebookb4.db.models.others;

import kg.peaksoft.ebookb4.db.models.bookClasses.Book;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import lombok.*;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
    @SequenceGenerator(name = "hibernate_seq", sequenceName = "basket_seq", allocationSize = 1)
    @Column(name = "basket_id")
    private Long basketId;

    @ManyToMany(mappedBy = "basked")
    private List<Book> books;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


}
