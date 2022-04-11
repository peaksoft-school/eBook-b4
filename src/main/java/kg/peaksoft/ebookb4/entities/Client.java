package kg.peaksoft.ebookb4.entities;

import javax.persistence.*;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "client_gen")
    @SequenceGenerator(
            name = "client_gen",
            sequenceName = "client_seq",
            allocationSize = 1)

    private Long clientId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;





}
