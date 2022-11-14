package kg.peaksoft.ebookb4.db.repository;

import kg.peaksoft.ebookb4.db.models.booksClasses.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {

    @Query("SELECT b.basketId FROM Basket b WHERE b.user.email = ?1")
    Long getUsersBasketId(String username);

    @Query(value = "SELECT case WHEN COUNT(*) > 0 then 1 else 0 end FROM books_basket WHERE basket_id = ?1 AND book_id = ?2", nativeQuery = true)
    Integer checkIfAlreadyClientPutInBasket(Long basketId, Long bookId);

}
