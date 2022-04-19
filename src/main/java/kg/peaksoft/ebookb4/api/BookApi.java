package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.bookClasses.Book;
import kg.peaksoft.ebookb4.db.service.BookService;
import kg.peaksoft.ebookb4.dto.request.BookRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_VENDOR')")
@Tag(name = "Books",description = "crud operations")
public class BookApi {

    private final BookService bookService;

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @Operation(summary = "We can find by id",description = "We can find by id in the store the book")
    @GetMapping("/{id}")
    public Book findById(@PathVariable Long id) {
        return bookService.findByBookId(id);
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @Operation(summary = "find All",description = "We can get all books from of the store")
    @GetMapping
    public List<Book> findAll(){
        return bookService.findAll();
    }

    @Operation(summary = "save book",description = "add a new book to the store")
    @PostMapping("/saveBook/{userId}")
    public ResponseEntity<?> saveBook(@RequestBody BookRequest bookRequest,
                                      @PathVariable("userId") Long userId){
        return  bookService.register(bookRequest, userId);
    }

    @Operation(summary = "delete",description = "We can delete by id book in the store")
    @DeleteMapping("/deleteBook/{bookId}")
    public ResponseEntity<?> delete(@PathVariable Long bookId) {
        return bookService.delete(bookId);
    }

    @Operation(summary = "update",description = "We can update or chage book here")
    @PatchMapping("/updateBook/{bookId}")
    public ResponseEntity<?> update(@RequestBody BookRequest request,
                                    @PathVariable Long bookId){
        return bookService.update(request,bookId);
    }

}