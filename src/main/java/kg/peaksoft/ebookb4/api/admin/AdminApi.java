package kg.peaksoft.ebookb4.api.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.db.service.AdminService;
import kg.peaksoft.ebookb4.db.service.BookGetService;
import kg.peaksoft.ebookb4.db.models.request.RefuseBookRequest;
import kg.peaksoft.ebookb4.db.models.request.Request;
import kg.peaksoft.ebookb4.db.models.response.BookResponse;
import kg.peaksoft.ebookb4.db.models.response.ClientResponse;
import kg.peaksoft.ebookb4.db.models.response.VendorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    @GetMapping("/books-in-process")
    public List<BookResponse> getAllBookRequest() {
        return bookGetService.getAllBooksRequests();
    }

    @Operation(summary = "Accept a book by id", description = "Admin accepts book by Id")
    @PostMapping("/book-accept")
    public ResponseEntity<?> acceptBookRequest(@RequestBody Request request) {
        return service.acceptBookRequest(request.getId());
    }

    @Operation(summary = "Get all accepted books", description = "All accepted books")
    @GetMapping("/books-accepted")
    public List<BookResponse> getAllAcceptedBooks() {
        return bookGetService.getAllAcceptedBooks();
    }

    @Operation(summary = "Refuse a book by id", description = "Admin refuses a book by id")
    @PostMapping("/book-refuse/{id}")
    public ResponseEntity<?> refuseBookRequest(@RequestBody RefuseBookRequest refuseBookRequest,
                                               @PathVariable Long id) {
        return service.refuseBookRequest(refuseBookRequest, id);
    }

    @Operation(summary = "Get refused books in admin panel", description = "Get refused books")
    @GetMapping("/vendor/{vendorId}/books-refused/{offset}")
    public List<Book> getBooksInCancel(@PathVariable Long vendorId,
                                       @PathVariable Integer offset) {
        return service.findBooksFromVendorCancelled(--offset, 12, vendorId,
                RequestStatus.REFUSED);
    }

    @Operation(summary = "Get books in progress in admin panel",
            description = "Get a books in progress")
    @GetMapping("vendor/{vendorId}/books-in-process/{offset}")
    public List<Book> getBooksInProcess(@PathVariable Long vendorId
            , @PathVariable Integer offset) {
        return service.findBooksFromVendorInProcess(--offset, 12, vendorId,
                RequestStatus.INPROGRESS);
    }

//    @GetMapping("/client/basket")
//    public List<BookResponse> getBooksClientFromBasket(Authentication authentication) {
//        return service.getBooksFromBasket(authentication.getName());
//    }
}