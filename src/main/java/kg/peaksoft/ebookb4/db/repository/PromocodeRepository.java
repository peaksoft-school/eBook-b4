package kg.peaksoft.ebookb4.db.repository;

import kg.peaksoft.ebookb4.db.models.booksClasses.Promocode;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 19/4/22
 */
public interface PromocodeRepository extends JpaRepository<Promocode, Long> {

    @Query("select case when count(s) > 0 then true else false end " +
            "from Promocode s where s.user = ?1 and s.isActive = true and s.endDay>?2  ")
    Boolean ifVendorAlreadyCreatedPromo(User userId, LocalDate today);

    @Query("select p from Promocode p where p.user = ?1 and p.isActive=true")
    Promocode getActivePromo(User user);

    @Query("select p from Promocode p")
    Optional<List<Promocode>> getPromos();
}
