package kg.peaksoft.ebookb4.db.models.others;

import kg.peaksoft.ebookb4.db.models.userClasses.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Promocode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
    @SequenceGenerator(name = "hibernate_seq", sequenceName = "promo_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String promocode;
    @CreatedDate
    private LocalDate beginningDay;
    private LocalDate endDay;
    private int discount;
    private Boolean isActive;

    @Override
    public String toString() {
        return "Promocode{" +
                "id=" + id +
                ", user=" + user +
                ", promocode='" + promocode + '\'' +
                ", beginningDay=" + beginningDay +
                ", endDay=" + endDay +
                ", discount=" + discount +
                ", isActive=" + isActive +
                '}';
    }
}