package kg.peaksoft.ebookb4.api.admin;

import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.db.service.impl.AdminServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminApi {

    private AdminServiceImpl service;
    private BookRepository repository;

    @GetMapping("/getBooks/")
    public List<Book> getAllBook() {
        return service.getBooks();
    }

    @GetMapping("/getBooksByBoth/{genre}/{bookType}")
    public List<Book> getBooksBy(@PathVariable Genre genre,
                                 @PathVariable BookType bookType) {
        return service.getBooksBy(genre, bookType);
    }

    @GetMapping("/getBooksByGenre/{genre}")
    public List<Book> getBooksByGenre(@PathVariable Genre genre) {
        return service.getBooksByGenre(genre);
    }

    @GetMapping("/getBooksByType/{bookType}")
    public List<Book> getBooksByBookType(@PathVariable BookType bookType) {
        return service.getBooksByBookType(bookType);
    }

//    @GetMapping("/getGenreCount/{genre}")
//    public List<Book> getBooksCount(@PathVariable Genre genre){
//        return repository.getBooksCount(genre);
//    }
    @GetMapping("/getGenreCount")
    public List<Book> getBooksCount(){
        return repository.getBooksCount();
    }

}
