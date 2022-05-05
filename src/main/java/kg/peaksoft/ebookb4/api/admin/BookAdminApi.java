package kg.peaksoft.ebookb4.api.admin;

import io.swagger.v3.oas.annotations.Operation;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/admin")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class BookAdminApi {

    private AdminService service;

    @Operation(summary = "Get all by genre and book type",
            description = "Filter all books by genre and book type ")
    @GetMapping("/booksByBoth/{genre}/{bookType}")
    public List<Book> getBooksBy(@PathVariable String genre,
                                 @PathVariable BookType bookType) {
        return service.getBooksBy(genre.toUpperCase(), bookType);
    }

    @Operation(summary = "Get books by genre", description = "Filter all books only by genre ")
    @GetMapping("/booksByGenre/{genre}")
    public List<Book> getBooksByGenre(@PathVariable String genre) {
        return service.getBooksByGenre(genre.toUpperCase());
    }

    @Operation(summary = "Get all books by book type",
            description = "Filter all books only by book type ")
    @GetMapping("/booksByType/{bookType}")
    public List<Book> getBooksByBookType(@PathVariable BookType bookType) {
        return service.getBooksByBookType(bookType);
    }

    @Operation(summary = "Delete book", description = "Delete books by id")
    @DeleteMapping("/removeBook/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long id) {
        return service.deleteBookById(id);
    }

    @Operation(summary = "Get book by id", description = "Change color of book when admin watch is true")
    @GetMapping("/book/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        return service.getBookById(id);
    }
}
