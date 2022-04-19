package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.bookClasses.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.enums.Language;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.db.service.BookGetService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class BookGetServiceImpl implements BookGetService {

    private final BookRepository repository;

    @Override
    public List<Book> getByPrice(BigDecimal min, BigDecimal max) {
        return repository.getByPrice(min,max);
    }

    @Override
    public List<Book> bookGetAudio() {
        return repository.bookGetAudio();
    }

    @Override
    public List<Book> bookGetPaper() {
        return repository.bookGetPaper();
    }

    @Override
    public List<Book> bookGetElectronic() {
        return repository.bookGetElectronic();
    }

    @Override
    public List<Book> findByBookType(BookType bookType) {
        return repository.findByBookType(bookType);
    }

    @Override
    public List<Book> findByGenre(Genre genre) {
        return repository.findByGenre(genre);
    }

    @Override
    public List<Book> findByLanguage(Language language) {
        return repository.findByLanguage(language);
    }

    @Override
    public List<Book> findAll(String name) {
        return repository.findAll(name);
    }
}
