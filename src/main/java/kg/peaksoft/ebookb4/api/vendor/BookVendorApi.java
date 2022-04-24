package kg.peaksoft.ebookb4.api.vendor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.service.BookService;
import kg.peaksoft.ebookb4.dto.request.BookRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/books/vendor")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_VENDOR')")
@Tag(name = "Books",description = "crud operations")
public class BookVendorApi {

    private final BookService bookService;

    @Operation(summary = "Save book",description = "Add a new book")
    @PostMapping("/saveBook")
    public ResponseEntity<?> saveBook(@RequestBody BookRequest bookRequest,
                                      Authentication authentication){
        return  bookService.register(bookRequest, authentication.getName());
    }

    @Operation(summary = "Delete book",description = "delete a book")
    @DeleteMapping("/deleteBook/{bookId}")
    public ResponseEntity<?> delete(@PathVariable Long bookId) {
        return bookService.delete(bookId);
    }

    @Operation(summary = "Update book",description = "update a book")
    @PatchMapping("/updateBook/{bookId}")
    public ResponseEntity<?> update(@RequestBody BookRequest request,
                                    @PathVariable Long bookId){
        return bookService.update(request,bookId);
    }

    @Operation(summary = "Get book for vendor/admin",
            description = "Get book by id for vendor")
    @GetMapping("/getBookById{id}")
    public Book getBookById(@PathVariable Long id){
        return bookService.findByBookId(id);
    }

    @Operation(summary = "Get all books for vendor/admin",
            description = "Get all books by id for vendor")
    @GetMapping("/getBooks/{offset}")
    public List<Book> getBooks(@PathVariable Integer offset,Authentication authentication){
        return bookService.findBooksFromVendor(--offset, 12, authentication.getName());
    }

}
