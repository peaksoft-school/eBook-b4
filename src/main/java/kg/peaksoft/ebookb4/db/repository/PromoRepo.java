package kg.peaksoft.ebookb4.db.repository;

import kg.peaksoft.ebookb4.db.models.booksClasses.Promocode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoRepo extends JpaRepository<Promocode,Long> {

}
