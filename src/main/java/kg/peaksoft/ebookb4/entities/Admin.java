package kg.peaksoft.ebookb4.entities;

import javax.persistence.*;

@Entity
@Table(name = "admins")
public class Admin {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long adminId;
    private String firstName;
    private String lastName;
    private String email;
}
