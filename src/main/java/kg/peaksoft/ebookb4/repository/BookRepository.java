package kg.peaksoft.ebookb4.repository;

import kg.peaksoft.ebookb4.models.bookClasses.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
