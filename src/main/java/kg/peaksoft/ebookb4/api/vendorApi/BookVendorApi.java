package kg.peaksoft.ebookb4.api.vendorApi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.service.PromoService;
import kg.peaksoft.ebookb4.dto.request.BookRequest;
import kg.peaksoft.ebookb4.db.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private PromoService promoService;


    @Operation(summary = "save book",description = "save a new book")
    @PostMapping("/saveBook")
    public ResponseEntity<?> saveBook(@RequestBody BookRequest bookRequest){
        return  bookService.register(bookRequest, SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Operation(summary = "delete book",description = "delete a book")
    @DeleteMapping("/deleteBook/{bookId}")
    public ResponseEntity<?> delete(@PathVariable Long bookId) {
        return bookService.delete(bookId);
    }

    @Operation(summary = "update book",description = "update a book")
    @PatchMapping("/updateBook/{bookId}")
    public ResponseEntity<?> update(@RequestBody BookRequest request,
                                    @PathVariable Long bookId){
        return bookService.update(request,bookId);
    }

    @Operation(summary = "get book for vendor/admin", description = "get book by id for vendor/admin")
    @GetMapping("/getBookById{id}")
    public Book getBookById(@PathVariable Long id){
        return bookService.findByBookId(id);
    }

    @Operation(summary = "get all books for vendor/admin", description = "get all books by id for vendor/admin")
    @GetMapping("/getBooks/{offset}")
    public List<Book> getBooks(@PathVariable Integer offset){
        return bookService.findAll(--offset, 12);
    }

}
