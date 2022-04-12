package kg.peaksoft.ebookb4.models.userClasses;

import kg.peaksoft.ebookb4.models.bookClasses.Basket;
import kg.peaksoft.ebookb4.models.bookClasses.Book;
import kg.peaksoft.ebookb4.models.bookClasses.Favorites;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "email")
    })
@Getter @Setter
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
