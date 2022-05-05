package kg.peaksoft.ebookb4.db.models.entity;

import kg.peaksoft.ebookb4.db.models.enums.ERole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter @Setter
@Component
@NoArgsConstructor
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
  @SequenceGenerator(name = "hibernate_seq", sequenceName = "role_seq", allocationSize = 1)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private ERole name;

  public Role(ERole name) {
    this.name = name;
  }


}