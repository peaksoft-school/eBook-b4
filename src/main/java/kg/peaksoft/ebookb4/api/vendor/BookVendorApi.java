package kg.peaksoft.ebookb4.api.vendor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.dto.dto.others.BookDTO;
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
@Tag(name = "Books vendor",description = "crud operations ...")
public class BookVendorApi {

    private final BookService bookService;

    @Operation(summary = "Save book",description = "Add a new book")
    @PostMapping("/saveBook")
    public ResponseEntity<?> saveBook(@RequestBody BookDTO bookDTO, Authentication authentication){
        return  bookService.register(bookDTO, authentication.getName());
    public ResponseEntity<?> saveBook(@RequestBody BookRequest bookRequest,
                                      Authentication authentication){
        return  bookService.register(bookRequest, authentication.getName());
    }

    @Operation(summary = "delete book",description = "delete a book")
    @DeleteMapping("/deleteBook/{bookId}")
    public ResponseEntity<?> delete(@PathVariable Long bookId) {
        return bookService.delete(bookId);
    }

    @Operation(summary = "update book",description = "update a book")
    @PatchMapping("/updateBook/{bookId}")
    public ResponseEntity<?> update(@RequestBody BookDTO request,
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
    public List<Book> getBooksOfVendor(@PathVariable Integer offset,Authentication authentication){
        return bookService.findBooksFromVendor(--offset, 12, authentication.getName());
    }

    @GetMapping("/getLikedBooks/{offset}")
    public List<Book> getLikedBooks(@PathVariable Integer offset, Authentication authentication){
        return bookService.findBooksFromVendorInFavorites(--offset, 12, authentication.getName());
    }

    @GetMapping("/getBooksAddedToBasket/{offset}")
    public List<Book> getBooksAddedToBasket(@PathVariable Integer offset, Authentication authentication){
        return bookService.findBooksFromVendorAddedToBasket(--offset, 12, authentication.getName());
    }

    @GetMapping("/getBooksWithDiscount/{offset}")
    public List<Book> getBooksWithDiscount(@PathVariable Integer offset, Authentication authentication){
        return bookService.findBooksFromVendorWithDiscount(--offset, 12, authentication.getName());
    }

    @GetMapping("/getBooksInCancel/{offset}")
    public List<Book> getBooksInCancel(@PathVariable Integer offset, Authentication authentication){
        return bookService.findBooksFromVendorCancelled(--offset, 12, authentication.getName());
    }

    @GetMapping("/getBooksInProgress/{offset}")
    public List<Book> getBooksInProgress(@PathVariable Integer offset, Authentication authentication){
        return bookService.findBooksFromVendorInProgress(--offset, 12, authentication.getName());
    }





}
