package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.db.models.bookClasses.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.enums.Language;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BookGetService {

    List<Book> getByPrice(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    List<Book> bookGetAudio();

    List<Book> bookGetPaper();

    List<Book> bookGetElectronic();

    List<Book> findByBookType(BookType bookType);

    List<Book> findByGenre(Genre genre);

    List<Book> findByLanguage(Language language);


}
