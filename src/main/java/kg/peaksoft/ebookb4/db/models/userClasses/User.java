package kg.peaksoft.ebookb4.db.models.userClasses;

import kg.peaksoft.ebookb4.db.models.bookClasses.Book;
import kg.peaksoft.ebookb4.db.models.others.Basket;
import kg.peaksoft.ebookb4.db.models.others.Favorites;
import kg.peaksoft.ebookb4.db.models.others.Promocode;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
    @SequenceGenerator(name = "hibernate_seq", sequenceName = "user_seq", allocationSize = 1,
    initialValue = 2)
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

    @OneToMany
    @Column(name = "vendor_added_books_id")
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
    private List<Book> vendorAddedBooks;

    @OneToMany
    @JoinTable(
            name = "user_liked_books",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "user_id"

            ),
            inverseJoinColumns = @JoinColumn(
                    name = "favorite_id",
                    referencedColumnName = "favorite_id"
            )
    )
    private List<Favorites> likedBooks;

    @OneToOne(mappedBy = "user")
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @OneToMany(mappedBy = "user")
    private List<Promocode> promocodes;

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
                ", password='" + password + '\'' +
                ", number='" + number + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", vendor added books=" + vendorAddedBooks +
                ", likedBooks=" + likedBooks +
                ", basket=" + basket +
                ", role=" + role +
                '}';
    }
}
