package kg.peaksoft.ebookb4.repository;

import kg.peaksoft.ebookb4.models.bookClasses.AudioBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioBookRepository extends JpaRepository<AudioBook,Long> {
}
