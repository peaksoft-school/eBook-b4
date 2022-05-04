package kg.peaksoft.ebookb4.db.repository;

import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.dto.ClientOperationDTO;
import kg.peaksoft.ebookb4.dto.response.BookResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    //find books by title, author, publishingHouse
    @Query("select  b from Book b  where b.title like %?1% " +
            "or b.authorFullName like %?1% " +
            "or b.publishingHouse like %?1% " +
            "or b.genre = ?1 " +
            "and b.requestStatus=?2 ")
    List<Book> findByName(String name, RequestStatus requestStatus);

    //find books by genre / admin panel
    @Query(value = "select b  from Book b where b.genre = ?1 and b.requestStatus = ?2 ")
    List<Book> findAllByGenre(Genre genre, RequestStatus requestStatus);

    //find all active books
    @Query("select b from Book b where b.requestStatus = ?1")
    List<Book> findAllActive(RequestStatus requestStatus);

    //find book by id and active one
    @Query("select b from Book b where b.bookId = ?1 and b.requestStatus = ?2")
    Optional<Book> findBookByIdAndActive(Long id, RequestStatus requestStatus);

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


    @Query("select u.basket.books from User u where u.email = :clientId")
    List<Book> findBasketByClientId(@Param("clientId") String name);


    @Query("select b from Book b where b.discount is not null and b.user.email = ?1")
    List<Book> findBooksFromVendorWithDiscount(String username);

    @Query("select b from Book b where b.requestStatus = ?2 and b.user.email = ?1")
    List<Book> findBooksFromVendorWithCancel(String username, RequestStatus requestStatus);

    @Query("select b from Book b where b.requestStatus = ?2 and b.user.email = ?1")
    List<Book> findBooksFromVendorInProgress(String username, RequestStatus requestStatus);

    @Transactional
    @Modifying
    @Query("update Book b set b.likes = b.likes+1 where b.bookId = ?1")
    void incrementLikesOfBook(Long bookId);

    @Transactional
    @Modifying
    @Query("update Book b set b.baskets = b.baskets+1 where b.bookId = ?1")
    void incrementBasketsOfBooks(Long bookId);

    //find books by BookType / admin panes
    @Query("select b from Book b where b.bookType = ?1 and b.requestStatus = ?2")
    List<Book> findAllByBookType(BookType bookType, RequestStatus requestStatus);
    //fin books by genre and book type /admin panel

    @Query("select b from Book b where b.genre =?1 or b.bookType= ?2 and b.requestStatus = ?3")
    List<Book> getBooks(Genre genre, BookType bookType, RequestStatus requestStatus);

    @Query("select new kg.peaksoft.ebookb4.dto.response.BookResponse(b.bookId, b.title, b.authorFullName, b.aboutBook, b.publishingHouse, " +
            "b.yearOfIssue, b.price) from Book b where b.requestStatus = ?1")
    List<BookResponse> findBooksInProgress(RequestStatus requestStatus);

    @Query("select new kg.peaksoft.ebookb4.dto.response.BookResponse(b.bookId, b.title, b.authorFullName, b.aboutBook, b.publishingHouse, " +
            "b.yearOfIssue, b.price) from Book b where b.requestStatus = ?1")
    List<BookResponse> findBooksAccepted(RequestStatus requestStatus);

    @Query("select b from Book b where b.requestStatus = ?2 and b.bookId = ?1")
    Optional<Book> findBookInProgress(Long bookId, RequestStatus requestStatus);

    @Query("select count (b) from Book b where b.genre =?1 and b.requestStatus = ?2")
    Integer getCountGenre(Genre genre, RequestStatus requestStatus);

    @Query(value = "select case when count(*) > 0 then 1 else 0 end " +
            "from liked_books where book_id = ?1 and user_id = ?2", nativeQuery = true)
    Integer checkIfAlreadyPutLike(Long bookId, Long userId);

    @Query("select b.likedBooks from Book b")
    List<Book> clientLikeBooks();

    @Query("select b from Book b where b.isBestSeller = true")
    List<Book> findAllByIsBestSeller();


    @Query("select b from Book b where b.dateOfRegister between ?1 and ?2")
    List<Book> booksNovelties(LocalDate localDate1,LocalDate localDate2);
}