package kg.peaksoft.ebookb4.db.repository;

import kg.peaksoft.ebookb4.db.models.booksClasses.PromoCode;
import kg.peaksoft.ebookb4.db.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface PromocodeRepository extends JpaRepository<PromoCode, Long> {

    @Query("select case when count(s) > 0 then true else false end " +
            "from PromoCode s where s.user = ?1 and s.endDay>?2  ")
    Boolean ifVendorAlreadyCreatedPromo(User userId, LocalDate today);

    @Query("select p from PromoCode p where p.user = ?1 and p.isActive=true")
    PromoCode getActivePromo(User user);

    @Query("select p from PromoCode p")
    Optional<List<PromoCode>> getPromos();
}
