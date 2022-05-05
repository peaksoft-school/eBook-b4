package kg.peaksoft.ebookb4.db.models.booksClasses;

import kg.peaksoft.ebookb4.db.models.entity.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promocode promocode1 = (Promocode) o;
        return discount == promocode1.discount && Objects.equals(id, promocode1.id) && Objects.equals(user, promocode1.user) && Objects.equals(promocode, promocode1.promocode) && Objects.equals(beginningDay, promocode1.beginningDay) && Objects.equals(endDay, promocode1.endDay) && Objects.equals(isActive, promocode1.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, promocode, beginningDay, endDay, discount, isActive);
    }
}