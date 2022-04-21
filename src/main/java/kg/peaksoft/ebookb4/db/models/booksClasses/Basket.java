package kg.peaksoft.ebookb4.db.models.booksClasses;

import kg.peaksoft.ebookb4.db.models.books.Book;
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

    @ManyToMany
    @JoinTable(
            name = "basket_books",
            joinColumns = @JoinColumn(
                    name = "basket_id",
                    referencedColumnName = "basket_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "book_id",
                    referencedColumnName = "book_id"))
    private List<Book> books;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


}