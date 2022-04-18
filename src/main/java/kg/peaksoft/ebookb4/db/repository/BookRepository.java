package kg.peaksoft.ebookb4.db.repository;

import kg.peaksoft.ebookb4.db.models.bookClasses.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookByTitle(String book);

    //    @Query(value = "select s from Book s where s.price between :min and :max")
    @Query(value = "select s from Book s where s.price > :min and s.price < :max")
    List<Book> getByPrice(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    @Query("select b from Book b where b.audioBook is not null")
    List<Book> bookGetAudio();

    @Query("select b from Book b where b.paperBook is not null")
    List<Book> bookGetPaper();

    @Query("select b from Book b where b.electronicBook is not null")
    List<Book> bookGetElectronic();

    @Query("select b from Book b where b.isBestSeller = true ")
    List<Book> bookGetBestseller();

    @Query("SELECT b FROM Book b WHERE b.bookType = ?1")
    List<Book> findByBookType(BookType bookType);

    @Query("SELECT b FROM Book b WHERE b.genre = ?1")
    List<Book> findByGenre(Genre genre);

}
