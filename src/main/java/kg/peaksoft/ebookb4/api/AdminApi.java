package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.dto.request.GenreRequest;
import kg.peaksoft.ebookb4.dto.request.RefuseBookRequest;
import kg.peaksoft.ebookb4.dto.request.Request;
import kg.peaksoft.ebookb4.dto.response.BookResponse;
import kg.peaksoft.ebookb4.dto.response.ClientResponse;
import kg.peaksoft.ebookb4.dto.response.CountForAdmin;
import kg.peaksoft.ebookb4.dto.response.VendorResponse;
import kg.peaksoft.ebookb4.db.service.AdminService;
import kg.peaksoft.ebookb4.db.service.BookGetService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Admin API", description = "Admin accessible endpoints")
public class AdminApi {

    private final AdminService service;
    private final BookGetService bookGetService;

    @Operation(summary = "Get all books in process", description = "Get books in process")
    @GetMapping("books-in-process/{offset}")
    public List<BookResponse> getAllBookRequest(@PathVariable Integer offset) {
        return bookGetService.getAllBooksInProgress(--offset, 8);
    }

    @Operation(summary = "Accept a book by id", description = "Admin accepts book by Id")
    @PostMapping("book-accept")
    public ResponseEntity<?> acceptBookRequest(@RequestBody Request request) {
        return service.acceptBookRequest(request.getId());
    }

    @Operation(summary = "Get all accepted books", description = "All accepted books")
    @GetMapping("books-accepted/{offset}")
    public List<Book> getAllAcceptedBooks(@RequestParam(required = false) Long genreId,
                                          @RequestParam(required = false) BookType bookType,
                                          @PathVariable Integer offset) {
        return bookGetService.getAllAcceptedBooks(--offset, 12, genreId, bookType);
    }

    @Operation(summary = "Refuse a book by id", description = "Admin refuses a book by id")
    @PostMapping("book-refuse/{id}")
    public ResponseEntity<?> refuseBookRequest(@RequestBody RefuseBookRequest refuseBookRequest,
                                               @PathVariable Long id) {
        return service.refuseBookRequest(refuseBookRequest, id);
    }

    @Operation(summary = "Delete client/vendor ", description = "Admin can delete client and vendor!")
    @DeleteMapping({"user/{id}"})
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return service.deleteByIdAdmin(id);
    }

    @Operation(summary = "Delete book", description = "Delete books by id")
    @DeleteMapping("book/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long id) {
        return service.deleteBookById(id);
    }

    @Operation(summary = "Get book by id", description = "Change color of book when admin watch is true")
    @GetMapping("book/{id}")
    public Book getBookById(@PathVariable Long id) {
        return service.getBookById(id);
    }

    @Operation(summary = "Get all client", description = "Get all client")
    @GetMapping("clients")
    public List<ClientResponse> getAllClient() {
        return service.findAllClient();
    }

    @Operation(summary = "Get client by id")
    @GetMapping("client/{id}")
    public ClientResponse getClientById(@PathVariable Long id) {
        return service.getClientById(id);
    }

    @Operation(summary = "Get books from client basket")
    @GetMapping("client/basket/{clientId}")
    public List<BookResponse> getBooksClientFromBasket(@PathVariable Long clientId) {
        return service.getBooksFromBasket(clientId);
    }

    @Operation(summary = "Get client liked books")
    @GetMapping("client/favorites/{clientId}")
    public List<BookResponse> booksClientFavorites(@PathVariable Long clientId) {
        return service.getAllLikedBooks(clientId);
    }

    @Operation(summary = "Get all purchased book in client")
    @GetMapping("client/operation/{clientId}")
    public List<Book> getBook(@PathVariable Long clientId) {
        return service.getBooksInPurchased(clientId);
    }

    @Operation(summary = "Get all Vendors", description = "Get all vendors with amount of books")
    @GetMapping("vendors")
    public List<VendorResponse> getAllVendors() {
        return service.findAllVendors();
    }

    @CrossOrigin
    @Operation(summary = "Get vendor by id")
    @GetMapping("vendor/{id}")
    public VendorResponse getByVendorId(@PathVariable Long id) {
        return service.getVendorById(id);
    }

    @Operation(summary = "Get all books of vendor in admin panel", description = "Get all books by vendor id")
    @GetMapping("vendor/{id}/books/{offset}")
    public List<Book> getBooksOfVendor(@PathVariable Long id, @PathVariable Integer offset) {
        return service.findBooksFromVendor(--offset, 12, id);
    }

    @Operation(summary = "Get books with likes in admin panel",
            description = "Get books with at least one like")
    @GetMapping("vendor/{vendorId}/favorite-books/{offset}")
    public List<Book> getLikedBooks(@PathVariable Long vendorId, @PathVariable Integer offset) {
        return service.findBooksFromVendorInFavorites(--offset, 12, vendorId);
    }

    @Operation(summary = "Get books in basket in admin panel", description = "Get books added to basket at least once")
    @GetMapping("vendor/{vendorId}/basket-books/{offset}")
    public List<Book> getBooksAddedToBasket(@PathVariable Long vendorId, @PathVariable Integer offset) {
        return service.findBooksFromVendorAddedToBasket(--offset, 12, vendorId);
    }

    @Operation(summary = "Get books with discount in admin panel", description = "Get books with discount")
    @GetMapping("vendor/{vendorId}/books-discount/{offset}")
    public List<Book> getBooksWithDiscount(@PathVariable Long vendorId, @PathVariable Integer offset) {
        return service.findBooksFromVendorWithDiscount(--offset, 12, vendorId);
    }

    @Operation(summary = "Get refused books in admin panel", description = "Get refused books")
    @GetMapping("vendor/{vendorId}/books-refused/{offset}")
    public List<Book> getBooksInCancel(@PathVariable Long vendorId, @PathVariable Integer offset) {
        return service.findBooksFromVendorCancelled(--offset, 12, vendorId, RequestStatus.REFUSED);
    }

    @Operation(summary = "Get books in progress in admin panel", description = "Get a books in process")
    @GetMapping("vendor/{vendorId}/books-in-process/{offset}")
    public List<Book> getBooksInProcess(@PathVariable Long vendorId, @PathVariable Integer offset) {
        return service.findBooksFromVendorInProcess(--offset, 12, vendorId, RequestStatus.INPROGRESS);
    }

    @Operation(summary = "Get two counts", description = "Get count of books in progress and books where admin didn't watch")
    @GetMapping("count-of-books-in-progress")
    public CountForAdmin getTwoCounts() {
        return service.getCountOfInProgressAlsoDontWatched();
    }

    @Operation(summary = "Get all genre count books", description = "Get genres count books")
    @GetMapping("genre-count")
    List<GenreRequest> getCountGenre() {
        return bookGetService.getCountGenre();
    }

}