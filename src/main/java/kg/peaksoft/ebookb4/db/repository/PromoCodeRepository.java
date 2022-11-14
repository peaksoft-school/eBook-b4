package kg.peaksoft.ebookb4.db.repository;

import kg.peaksoft.ebookb4.db.models.booksClasses.PromoCode;
import kg.peaksoft.ebookb4.db.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {

    @Query("SELECT case WHEN COUNT (s) > 0 then true else false end FROM PromoCode s WHERE s.user = ?1 AND s.endDay>?2  ")
    Boolean ifVendorAlreadyCreatedPromo(User userId, LocalDate today);

    @Query("SELECT p FROM PromoCode p WHERE p.user = ?1 AND p.isActive=true")
    PromoCode getActivePromo(User user);

    @Query("SELECT p FROM PromoCode p")
    Optional<List<PromoCode>> getPromos();

}
