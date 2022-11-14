package kg.peaksoft.ebookb4.db.repository;

import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.dto.response.BookResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    //find books by title, author, publishingHouse
    @Query("SELECT b FROM Book b WHERE b.title LIKE %?1% " +
            "OR b.genre.name LIKE %?1%" +
            "OR b.authorFullName LIKE %?1%" +
            "OR b.publishingHouse LIKE %?1% AND b.requestStatus=?2")
    List<Book> findByName(String name, RequestStatus requestStatus);

    //find all active books
    @Query("SELECT b FROM Book b WHERE b.requestStatus = ?1")
    List<Book> findAllActive(RequestStatus requestStatus);

    @Query("SELECT new kg.peaksoft.ebookb4.dto.response.BookResponse(b.bookId, b.title, b.authorFullName, " +
            "b.aboutBook, b.publishingHouse, b.dateOfRegister, b.price, b.adminWatch, b.fileInformation) " +
            "FROM Book b WHERE b.requestStatus =:requestStatus")
    List<BookResponse> findBooksInProgress(RequestStatus requestStatus);


    //find book by id and active one
    @Query("SELECT b FROM Book b WHERE b.bookId = ?1 AND b.requestStatus = ?2")
    Optional<Book> findBookByIdAndActive(Long id, RequestStatus requestStatus);

    //Find all books of current vendor
    @Query("SELECT b FROM Book b WHERE b.user.email = ?1")
    List<Book> findBooksFromVendor(String username);

    @Query("SELECT b FROM Book b WHERE b.likes>0 AND b.user.email = ?1")
    List<Book> findLikedBooksFromVendor(String username);

    @Query("SELECT b FROM Book b WHERE b.baskets>0 AND b.user.email = ?1")
    List<Book> findBooksFromVendorAddedToBasket(String username);

    @Query("SELECT u.basket.books FROM User u WHERE u.email = :name")
    List<Book> findBasketByClientId(@Param("name") String name);

    @Query("SELECT u.basket.books FROM User u WHERE u.id = ?1")
    List<Book> findBasketByClientIdAdmin(Long id);

    @Query("SELECT b FROM Book b WHERE b.discount is not null AND b.user.email = ?1")
    List<Book> findBooksFromVendorWithDiscount(String username);

    @Query("SELECT b FROM Book b WHERE b.requestStatus = ?2 AND b.user.email = ?1")
    List<Book> findBooksFromVendorWithCancel(String username, RequestStatus requestStatus);

    @Query("SELECT b FROM Book b WHERE b.requestStatus = ?2 AND b.user.email = ?1")
    List<Book> findBooksFromVendorInProgress(String username, RequestStatus requestStatus);

    @Transactional
    @Modifying
    @Query("UPDATE Book b SET b.likes = b.likes+1 WHERE b.bookId = ?1")
    void incrementLikesOfBook(Long bookId);

    @Transactional
    @Modifying
    @Query("UPDATE Book b SET b.baskets = b.baskets+1 WHERE b.bookId = ?1")
    void incrementBasketsOfBooks(Long bookId);

    @Query("SELECT b FROM Book b WHERE b.requestStatus = ?2 AND b.bookId = ?1")
    Optional<Book> findBookInProgress(Long bookId, RequestStatus requestStatus);

    //find books by genre / admin panel
    @Query(value = "SELECT b FROM Book b WHERE b.genre.name LIKE %?1% AND b.requestStatus = ?2 ")
    List<Book> findAllByGenre(String genreName, RequestStatus requestStatus);

    @Query("SELECT COUNT (b) FROM Book b WHERE b.genre.id = ?1 AND b.requestStatus = ?2")
    int getCountGenre(Long genre, RequestStatus requestStatus);

    @Query(value = "SELECT case WHEN count(*) > 0 then 1 else 0 end " +
            "FROM liked_books WHERE book_id = ?1 AND user_id = ?2", nativeQuery = true)
    Integer checkIfAlreadyPutLike(Long bookId, Long userId);

    @Query("SELECT b FROM Book b WHERE b.isBestSeller = true")
    List<Book> findAllByIsBestSeller();

    @Query("SELECT b FROM Book b WHERE b.isNew = true ORDER BY b.dateOfRegister DESC")
    List<Book> BooksNovelties(List<Book> books);

    @Transactional
    @Modifying
    @Query("UPDATE Book b SET b.isNew = false WHERE b.bookId = ?1")
    void updateBook(Long bookId);

    @Query("SELECT NEW kg.peaksoft.ebookb4.dto.response.BookResponse(b.bookId, b.title, " +
            "b.authorFullName, b.aboutBook, b.publishingHouse,b.dateOfRegister, b.price, b.adminWatch, b.fileInformation)" +
            "FROM Book b WHERE b.operations.size > 0 AND b.user.email = ?1 AND b.user.role.name = ?2")
    List<BookResponse> getVendorBooksSold(String name, ERole role);

    @Query(value = "SELECT * FROM book b " +
            "JOIN operation_books o ON o.book_id = b.book_id " +
            "JOIN client_operations t ON t.operation_id = o.operation_id " +
            "JOIN users u ON t.user_id = u.user_id " +
            "WHERE u.user_id = ?1 ", nativeQuery = true)
    List<Book> getBooksInPurchased(Long id);

    @Query(value = "SELECT COUNT (b) FROM Book b WHERE b.requestStatus =:requestStatus")
    Integer getCountOfBooksInProgress(RequestStatus requestStatus);

    @Query("SELECT b FROM  Book b WHERE  b.requestStatus =:requestStatus")
    List<Book> findOnlyInProgressBooks(RequestStatus requestStatus);

    @Query("SELECT b FROM Book b WHERE b.bookType = ?1 AND b.requestStatus = ?2")
    List<Book> getAllAudioBook(BookType bookType, RequestStatus requestStatus);

    @Query("SELECT b FROM Book b WHERE b.bookType = ?1 AND b.requestStatus = ?2")
    List<Book> getAllEBook(BookType ebook, RequestStatus requestStatus);

    @Query(value = "SELECT * FROM book ORDER BY title DESC limit 3;", nativeQuery = true)
    List<Book> getBook(RequestStatus accepted);

}