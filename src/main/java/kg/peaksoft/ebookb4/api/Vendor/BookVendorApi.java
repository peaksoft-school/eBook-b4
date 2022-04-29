package kg.peaksoft.ebookb4.api.Vendor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.dto.dto.others.BookDTO;
import kg.peaksoft.ebookb4.db.service.BookService;
import kg.peaksoft.ebookb4.dto.request.Request;
import lombok.AllArgsConstructor;
import nonapi.io.github.classgraph.utils.LogNode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/vendor/books")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_VENDOR')")
@Tag(name = "Books vendor",description = "crud operations ...")
public class BookVendorApi {

    private final BookService bookService;

    @Operation(summary = "Save book",description = "Adding a new book")
    @PostMapping("/new-book")
    public ResponseEntity<?> saveBook(@RequestBody BookDTO bookDTO, Authentication authentication){
        return  bookService.saveBook(bookDTO, authentication.getName());
    }

    @Operation(summary = "Delete book",description = "Deleting a book by id")
    @DeleteMapping("/remove/{bookId}")
    public ResponseEntity<?> delete(@PathVariable Long bookId) {
        return bookService.delete(bookId);
    }

    @Operation(summary = "Update book",description = "update a book")
    @PatchMapping("/editing/{id}")
    public ResponseEntity<?> update(@RequestBody BookDTO request,
                                    @PathVariable Long id){
        return bookService.update(request,id);
    }

    @Operation(summary = "Get book",
            description = "Get book by id for vendor")
    @GetMapping("/book{id}")
    public Book getBookById(@PathVariable Long id){
        return bookService.findByBookId(id);
    }

    @Operation(summary = "Get all books for vendor",
            description = "Get all books by token")
    @GetMapping("/books/{offset}")
    public List<Book> getBooksOfVendor(@PathVariable Integer offset,Authentication authentication){
        return bookService.findBooksFromVendor(--offset, 12, authentication.getName());
    }

    @Operation(summary = "Get books with likes",
            description = "Get books with at least one like")
    @GetMapping("/favorite-books/{offset}")
    public List<Book> getLikedBooks(@PathVariable Integer offset, Authentication authentication){
        return bookService.findBooksFromVendorInFavorites(--offset, 12, authentication.getName());
    }

    @Operation(summary = "Get books in basket",
            description = "Get books added to basket at least once")
    @GetMapping("/basket-books/{offset}")
    public List<Book> getBooksAddedToBasket(@PathVariable Integer offset, Authentication authentication){
        return bookService.findBooksFromVendorAddedToBasket(--offset, 12, authentication.getName());
    }

    @Operation(summary = "Get books with discount",
            description = "Get books with discount")
    @GetMapping("/books-discount/{offset}")
    public List<Book> getBooksWithDiscount(@PathVariable Integer offset, Authentication authentication){
        return bookService.findBooksFromVendorWithDiscount(--offset, 12, authentication.getName());
    }

    @Operation(summary = "Get refused books",
            description = "Get refused books")
    @GetMapping("/books-refused/{offset}")
    public List<Book> getBooksInCancel(@PathVariable Integer offset, Authentication authentication){
        return bookService.findBooksFromVendorCancelled(--offset, 12, authentication.getName(),
                RequestStatus.REFUSED);
    }

    @Operation(summary = "Get books in progress",
            description = "Get a books in progress")
    @GetMapping("/books-in-process/{offset}")
    public List<Book> getBooksInProcess(@PathVariable Integer offset, Authentication authentication){
        return bookService.findBooksFromVendorInProcess(--offset, 12, authentication.getName(),
                RequestStatus.INPROGRESS);
    }
}
