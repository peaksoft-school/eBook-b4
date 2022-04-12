package kg.peaksoft.ebookb4.entities;

import kg.peaksoft.ebookb4.enums.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "gen")
    @SequenceGenerator(
            name = "gen",
            sequenceName = "seq",
            allocationSize = 1)

    private Long clientId;
    private String fullName;
    private Role role;
    private String email;
    private String address;
    private  int phoneNumber;

    @Transient
    private ApplicationUser applicationUser;

    @OneToMany
    private List<Promocode> promocode;

}
