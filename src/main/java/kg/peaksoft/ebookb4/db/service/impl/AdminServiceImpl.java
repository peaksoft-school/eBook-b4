package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import kg.peaksoft.ebookb4.db.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private BookRepository bookRepository;
    private UserRepository userRepository;

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBooksBy(Genre genre, BookType bookType) {
        return bookRepository.getBooks(genre, bookType);
    }

    @Override
    public List<Book> getBooksByGenre(Genre genre) {
        return bookRepository.findAllByGenre(genre);
    }

    @Override
    public List<Book> getBooksByBookType(BookType bookType) {
        return bookRepository.findAllByBookType(bookType);
    }
}
