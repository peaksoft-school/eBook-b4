package kg.peaksoft.ebookb4.db.models.userClasses;

import kg.peaksoft.ebookb4.db.models.bookClasses.Book;
import kg.peaksoft.ebookb4.db.models.others.Basket;
import kg.peaksoft.ebookb4.db.models.others.ClientOperations;
import kg.peaksoft.ebookb4.db.models.others.Favorites;
import kg.peaksoft.ebookb4.db.models.others.Promocode;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
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

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @OneToOne(mappedBy = "user")
    private Promocode promocodes;

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