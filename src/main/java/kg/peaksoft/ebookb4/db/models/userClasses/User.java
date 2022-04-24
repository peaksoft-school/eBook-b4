package kg.peaksoft.ebookb4.db.models.userClasses;

import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.booksClasses.Basket;
import kg.peaksoft.ebookb4.db.models.booksClasses.ClientOperations;
//import kg.peaksoft.ebookb4.db.models.booksClasses.Favorites;
import kg.peaksoft.ebookb4.db.models.booksClasses.Promocode;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
    @SequenceGenerator(name = "hibernate_seq", sequenceName = "user_seq", allocationSize = 1,
            initialValue = 1)
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 64, message = "Password must be 8-64 char long")
    private String password;
    private String number;
    private String firstName;
    private String lastName;

    private LocalDate dateOfRegistration;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user")
    private List<Book> vendorAddedBooks;

    @ManyToMany
    @JoinTable(
            name = "liked_books",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "book_id",
                    referencedColumnName = "book_id"))
    private List<Book> likedBooks;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @OneToMany(mappedBy = "user")
    private List<Promocode> promocodes;

    @OneToOne
    @JoinColumn(name = "operation_id")
    private ClientOperations clientOperation;

    @OneToOne
    private Role role;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}'+"\n";
    }
}