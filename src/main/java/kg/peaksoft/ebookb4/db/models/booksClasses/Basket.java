package kg.peaksoft.ebookb4.db.models.booksClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import lombok.*;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
    @SequenceGenerator(name = "hibernate_seq", sequenceName = "basket_seq", allocationSize = 1)
    @Column(name = "basket_id")
    private Long basketId;

    @ManyToMany
    @JoinTable(
            name = "books_basket"
            , joinColumns = @JoinColumn(name = "basket_id")
            , inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public void clear() {
        books.removeIf(books -> true);
    }
}
