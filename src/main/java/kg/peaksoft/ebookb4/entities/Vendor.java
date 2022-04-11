package kg.peaksoft.ebookb4.entities;

import javax.persistence.*;

@Entity
@Table(name = "vendors")
public class Vendor {


    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private int phoneNumber;

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "vendor_gen")
    @SequenceGenerator(
            name = "vendor_gen",
            sequenceName = "vendor_seq",
            allocationSize = 1)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
