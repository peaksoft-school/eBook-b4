package kg.peaksoft.ebookb4.db.models.booksClasses;

import kg.peaksoft.ebookb4.db.models.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromoCode {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_seq")
    @SequenceGenerator(name = "hibernate_seq", sequenceName = "promo_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String promoCode;

    @CreatedDate
    private LocalDate beginningDay;

    private LocalDate endDay;

    private int discount;

    private Boolean isActive;

    @Override
    public String toString() {
        return "PromoCode{" +
                "id=" + id +
                ", user=" + user +
                ", promoCode='" + promoCode + '\'' +
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
        PromoCode promoCode1 = (PromoCode) o;
        return discount == promoCode1.discount && Objects.equals(id, promoCode1.id) && Objects.equals(user, promoCode1.user) && Objects.equals(promoCode, promoCode1.promoCode) && Objects.equals(beginningDay, promoCode1.beginningDay) && Objects.equals(endDay, promoCode1.endDay) && Objects.equals(isActive, promoCode1.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, promoCode, beginningDay, endDay, discount, isActive);
    }

}