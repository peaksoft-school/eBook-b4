package kg.peaksoft.ebookb4.entities;

import javax.persistence.*;

@Entity
@Table(name = "admins")
public class Admin {


    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "admin_gen")
    @SequenceGenerator(
            name = "admin_gen",
            sequenceName = "admin_seq",
            allocationSize = 1)

    private Long adminId;
    private String firstName;
    private String lastName;
    private String email;
}
