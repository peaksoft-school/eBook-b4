package kg.peaksoft.ebookb4.models.userClasses;

import kg.peaksoft.ebookb4.models.others.Basket;
import kg.peaksoft.ebookb4.models.bookClasses.Book;
import kg.peaksoft.ebookb4.models.others.Favorites;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    private String number;

    private String firstName;

    private String lastName;

    @OneToMany
    @Column(name = "book_id")
    @JoinTable(
            name = "vendor_books",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "user_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "book_id",
                    referencedColumnName = "book_id"
            )
    )
    private List<Book> books;

    @OneToMany
    private List<Favorites> likedBooks;

    @OneToOne
    private Basket basket;

    @OneToOne
    private Role role;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
