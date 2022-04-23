package kg.peaksoft.ebookb4.db.repository;

import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    //find books by genre
    @Query("select b from Book b where b.genre = ?1 and b.isActive=true")
    List<Book> findAllByGenre(Genre genre);

    //find books by title, author, publishingHouse
    @Query("select b from Book b where b.title like %?1% " +
            "or b.authorFullName like %?1%" +
            "or b.publishingHouse like %?1% and b.isActive=true")
    List<Book> findByName(String keyword);

    //find all active books
    @Query("select b from Book b where b.isActive = true")
    List<Book> findAllActive();

    //find book by id and active one
    @Query("select b from Book b where b.bookId = ?1 and b.isActive = true")
    Optional<Book> findBookByIdAndActive(Long id);

    //change discountPromo to null if it is expired
    @Transactional
    @Modifying
    @Query("update Book b set b.discountFromPromo = null where b.user = ?1")
    void checkForPromos(User user);

    //We give here a promo
    @Transactional
    @Modifying
    @Query("update Book b set b.discountFromPromo = ?2 where b.user = ?1 and b.discount is null ")
    void givePromo(User user, int discount);

    //Find all books of current vendor
    @Query("select b from Book b where b.user.email = ?1")
    List<Book> findBooksFromVendor(String username);

    @Query("select b from Book b where b.likes>0 and b.user.email = ?1")
    List<Book> findLikedBooksFromVendor(String username);


    @Query("select b from Book b where b.baskets>0 and b.user.email = ?1")
    List<Book> findBooksFromVendorAddedToBasket(String username);

    @Query("select b from Book b where b.discount is not null and b.user.email = ?1")
    List<Book> findBooksFromVendorWithDiscount(String username);

    @Query("select b from Book b where b.isActive = false and b.user.email = ?1")
    List<Book> findBooksFromVendorWithCancel(String username);

    @Query("select b from Book b where b.isActive is null and b.user.email = ?1")
    List<Book> findBooksFromVendorInProgress(String username);

//    @Query(value = "select count(case when book_id = ?1 and user_id = ?2 then true else false end) from liked_books", nativeQuery = true)
    @Query(value = "select case when count(*) > 0 then 1 else 0 end " +
            "from liked_books where book_id = ?1 and user_id = ?2", nativeQuery = true)
    Integer checkIfAlreadyPutLike(Long bookId, Long userId);

    @Transactional
    @Modifying
    @Query("update Book b set b.likes = b.likes+1 where b.bookId = ?1")
    void incrementLikesOfBook(Long bookId);

    @Transactional
    @Modifying
    @Query("update Book b set b.baskets = b.baskets+1 where b.bookId = ?1")
    void incrementBasketsOfBooks(Long bookId);



}
