package kg.peaksoft.ebookb4;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin {
    @Id
    private Long adminId;
}
