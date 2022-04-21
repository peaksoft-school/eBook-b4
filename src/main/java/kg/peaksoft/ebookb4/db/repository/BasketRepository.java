package kg.peaksoft.ebookb4.db.repository;

import kg.peaksoft.ebookb4.db.models.others.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
}
