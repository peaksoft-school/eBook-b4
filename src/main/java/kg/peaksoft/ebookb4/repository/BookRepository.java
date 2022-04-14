package kg.peaksoft.ebookb4.repository;

import kg.peaksoft.ebookb4.models.bookClasses.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookByTitle(String book);
}
