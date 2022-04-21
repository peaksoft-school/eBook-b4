package kg.peaksoft.ebookb4.db.repository;

import kg.peaksoft.ebookb4.db.models.bookClasses.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.enums.Language;
import kg.peaksoft.ebookb4.db.models.others.SortBook;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.genre = ?1 and b.isActive=true")
    List<Book> findAllByGenre(Genre genre);

    @Query("select b from Book b where b.title like %?1% " +
            "or b.authorFullName like %?1%" +
            "or b.publishingHouse like %?1% and b.isActive=true")
    List<Book> findByName(String keyword);

    @Query("select b from Book b where b.isActive = true")
    List<Book> findAllActive();

    @Query("select b from Book b where b.bookId = ?1 and b.isActive = true")
    Optional<Book> findBookById(Long id);

    @Transactional
    @Modifying
    @Query("update Book b set b.discountFromPromo = null where b.user = ?1")
    void checkForPromos(User user);

    @Query("select b from Book b where b.user = ?1")
    List<Book> findBooksFromVendor(String username);

}
