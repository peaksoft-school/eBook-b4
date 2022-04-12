package kg.peaksoft.ebookb4.entities;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private List<Book> books;

    @OneToMany
    private List<Favorites> favorites;
    @OneToMany
    private List<Basket> basket;

    @OneToOne
    private Role role;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
