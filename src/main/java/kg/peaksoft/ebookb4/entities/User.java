package kg.peaksoft.ebookb4.entities;

import javax.persistence.*;

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

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private  int phoneNumber;

}
