package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.db.models.request.GenreRequest;
import kg.peaksoft.ebookb4.db.models.request.RefuseBookRequest;
import kg.peaksoft.ebookb4.db.models.request.Request;
import kg.peaksoft.ebookb4.db.models.response.BookResponse;
import kg.peaksoft.ebookb4.db.models.response.ClientResponse;
import kg.peaksoft.ebookb4.db.models.response.CountForAdmin;
import kg.peaksoft.ebookb4.db.models.response.VendorResponse;
import kg.peaksoft.ebookb4.db.service.AdminService;
import kg.peaksoft.ebookb4.db.service.BookGetService;
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
@Tag(name = "Admin", description = "Admin accessible apis")
public class AdminApi {

    private AdminService service;
    private BookGetService bookGetService;

    @Operation(summary = "Get all books in process", description = "Get books in process")
    @GetMapping("/books-in-process/{offset}")
    public List<BookResponse> getAllBookRequest(@PathVariable Integer offset) {
        return bookGetService.getAllBooksRequests(--offset, 8);
    }

    @Operation(summary = "Accept a book by id", description = "Admin accepts book by Id")
    @PostMapping("/book-accept")
    public ResponseEntity<?> acceptBookRequest(@RequestBody Request request) {
        return service.acceptBookRequest(request.getId());
    }

    @Operation(summary = "Get all accepted books", description = "All accepted books")
    @GetMapping("/books-accepted/{offset}")
    public List<BookResponse> getAllAcceptedBooks(@PathVariable Integer offset) {
        return bookGetService.getAllAcceptedBooks(--offset, 8);
    }

    @Operation(summary = "Refuse a book by id", description = "Admin refuses a book by id")
    @PostMapping("/book-refuse/{id}")
    public ResponseEntity<?> refuseBookRequest(@RequestBody RefuseBookRequest refuseBookRequest,
                                               @PathVariable Long id) {
        return service.refuseBookRequest(refuseBookRequest, id);
    }

    @Operation(summary = "Delete client/vendor ",
            description = "Admin can delete client and vendor!")
    @DeleteMapping({"/user/{userId}"})
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        return service.deleteById(userId);
    }

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
    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long bookId) {
        return service.deleteBookById(bookId);
    }

    @Operation(summary = "Get book by id", description = "Change color of book when admin watch is true")
    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable Long id) {
        return service.getBookById(id);
    }

    @Operation(summary = "Get all client",
            description = "Get all client ")
    @GetMapping("/clients")
    public List<ClientResponse> getAllClient() {
        return service.findAllClient();
    }

    @Operation(summary = "Get client by id")
    @GetMapping("/client/{id}")
    public ClientResponse getClientById(@PathVariable Long id) {
        return service.getClientById(id);
    }

    @Operation(summary = "Get books from client basket")
    @GetMapping("/client/basket/{clientId}")
    public List<BookResponse> getBooksClientFromBasket(@PathVariable Long clientId) {
        return service.getBooksFromBasket(clientId);
    }

    @Operation(summary = "Get client liked books")
    @GetMapping("/client/favorites/{clientId}")
    public List<BookResponse> booksClientFavorites(@PathVariable Long clientId) {
        return service.getAllLikedBooks(clientId);
    }

    @Operation(summary = "Get all purchased book in client")
    @GetMapping("/client/operation/{clientId}")
    public List<Book> getBook(@PathVariable Long clientId) {
        return service.getBooksInPurchased(clientId);
    }


    @Operation(summary = "Get all Vendors",
            description = "Get all vendors with amount of books")
    @GetMapping("/vendors")
    public List<VendorResponse> getAllVendors() {
        return service.findAllVendors();
    }

    @CrossOrigin
    @Operation(summary = "Get vendor by id")
    @GetMapping("/vendor/{id}")
    public VendorResponse getByVendorId(@PathVariable Long id) {
        return service.getVendorById(id);
    }

    @Operation(summary = "Get all books of vendor in admin panel",
            description = "Get all books by vendor id")
    @GetMapping("/vendor/{vendorId}/books/{offset}")
    public List<Book> getBooksOfVendor(@PathVariable Long vendorId,
                                       @PathVariable Integer offset) {
        return service.findBooksFromVendor(--offset, 12, vendorId);
    }

    @Operation(summary = "Get books with likes in admin panel",
            description = "Get books with at least one like")
    @GetMapping("/vendor/{vendorId}/favorite-books/{offset}")
    public List<Book> getLikedBooks(@PathVariable Long vendorId, @PathVariable Integer offset) {
        return service.findBooksFromVendorInFavorites(--offset, 12, vendorId);
    }

    @Operation(summary = "Get books in basket in admin panel",
            description = "Get books added to basket at least once")
    @GetMapping("/vendor/{vendorId}/basket-books/{offset}")
    public List<Book> getBooksAddedToBasket(@PathVariable Long vendorId,
                                            @PathVariable Integer offset) {
        return service.findBooksFromVendorAddedToBasket(--offset, 12, vendorId);
    }

    @Operation(summary = "Get books with discount in admin panel",
            description = "Get books with discount")
    @GetMapping("/vendor/{vendorId}/books-discount/{offset}")
    public List<Book> getBooksWithDiscount(@PathVariable Long vendorId
            , @PathVariable Integer offset) {
        return service.findBooksFromVendorWithDiscount(--offset, 12, vendorId);
    }

    @Operation(summary = "Get refused books in admin panel", description = "Get refused books")
    @GetMapping("/vendor/{vendorId}/books-refused/{offset}")
    public List<Book> getBooksInCancel(@PathVariable Long vendorId,
                                       @PathVariable Integer offset) {
        return service.findBooksFromVendorCancelled(--offset, 12, vendorId,
                RequestStatus.REFUSED);
    }

    @Operation(summary = "Get books in progress in admin panel",
            description = "Get a books in process")
    @GetMapping("/vendor/{vendorId}/books-in-process/{offset}")
    public List<Book> getBooksInProcess(@PathVariable Long vendorId
            , @PathVariable Integer offset) {
        return service.findBooksFromVendorInProcess(--offset, 12, vendorId,
                RequestStatus.INPROGRESS);
    }

    @Operation(summary = "Get two counts",
            description = "get count of books in progress also get count of books where admin didn't watch")
    @GetMapping("/countOfBooksInProgress")
    public CountForAdmin getTwoCounts() {
        return service.getCountOfInProgressAlsoDontWatched();
    }

    @Operation(summary = "Get all genre count books", description = "get genres countBooks")
    @GetMapping("/genre_count")
    List<GenreRequest> getCountGenre() {
        return bookGetService.getCountGenre();
    }
}