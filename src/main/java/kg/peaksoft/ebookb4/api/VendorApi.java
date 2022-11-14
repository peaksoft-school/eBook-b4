package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.dto.BookDTO;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.entity.Genre;
import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.dto.response.BookResponse;
import kg.peaksoft.ebookb4.dto.response.BookResponseAfterSaved;
import kg.peaksoft.ebookb4.dto.response.CountForAdmin;
import kg.peaksoft.ebookb4.db.service.BookService;
import kg.peaksoft.ebookb4.db.service.PromoService;
import kg.peaksoft.ebookb4.db.service.VendorService;
import kg.peaksoft.ebookb4.dto.request.PromoRequest;
import kg.peaksoft.ebookb4.dto.VendorRegisterDTO;
import kg.peaksoft.ebookb4.dto.VendorUpdateDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/vendors")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ROLE_VENDOR')")
@Tag(name = "Vendor", description = "Vendor accessible apis")
public class VendorApi {

    private final PromoService promoService;
    private final VendorService vendorService;
    private final BookService bookService;

    @Operation(summary = "Promo", description = "Vendor creates a promo")
    @PostMapping("promo")
    public ResponseEntity<?> createPromo(@RequestBody PromoRequest promoRequest, Authentication authentication) {
        return promoService.createPromo(promoRequest, authentication.getName());
    }

    @Operation(summary = "Vendor profile", description = "All accessible data of client")
    @GetMapping("profile")
    public VendorRegisterDTO getVendorDetails(Authentication authentication) {
        return vendorService.getVendorDetails(authentication.getName());
    }

    @Operation(summary = "Update vendor", description = "Updating vendor profile")
    @PatchMapping("profile/settings")
    public ResponseEntity<?> updateVendor(@RequestBody VendorUpdateDTO newVendorDTO, Authentication authentication) {
        return vendorService.update(newVendorDTO, authentication.getName());
    }

    @Operation(summary = "Save book", description = "Adding a new book")
    @PostMapping("new-book")
    public BookResponseAfterSaved saveBook(@RequestBody BookDTO bookDTO, Authentication authentication) {
        return bookService.saveBook(bookDTO, authentication.getName());
    }

    @Operation(summary = "Delete book", description = "Deleting a book by id")
    @DeleteMapping("{bookId}")
    public ResponseEntity<?> delete(@PathVariable Long bookId) {
        return bookService.delete(bookId);
    }

    @Operation(summary = "Update book", description = "update a book")
    @PatchMapping("{bookId}")
    public ResponseEntity<?> update(@RequestBody BookDTO request, @PathVariable Long bookId) {
        Long genreId = request.getGenreId();
        return bookService.update(request, bookId, genreId);
    }

    @Operation(summary = "Get book", description = "Get book by id for vendor")
    @GetMapping("{bookId}")
    public Book getBookById(@PathVariable Long bookId) {
        return bookService.findByBookId(bookId);
    }

    @Operation(summary = "Get all books for vendor", description = "Get all books by token")
    @GetMapping("books/{offset}")
    public List<Book> getBooksOfVendor(@PathVariable Integer offset, Authentication authentication) {
        return bookService.findBooksFromVendor(--offset, 12, authentication.getName());
    }

    @Operation(summary = "Get books with likes", description = "Get books with at least one like")
    @GetMapping("favorite-books/{offset}")
    public List<Book> getLikedBooks(@PathVariable Integer offset, Authentication authentication) {
        return bookService.findBooksFromVendorInFavorites(--offset, 12, authentication.getName());
    }

    @Operation(summary = "Get books in basket", description = "Get books added to basket at least once")
    @GetMapping("basket-books/{offset}")
    public List<Book> getBooksAddedToBasket(@PathVariable Integer offset, Authentication authentication) {
        return bookService.findBooksFromVendorAddedToBasket(--offset, 12, authentication.getName());
    }

    @Operation(summary = "Get books with discount", description = "Get books with discount")
    @GetMapping("books-discount/{offset}")
    public List<Book> getBooksWithDiscount(@PathVariable Integer offset, Authentication authentication) {
        return bookService.findBooksFromVendorWithDiscount(--offset, 12, authentication.getName());
    }

    @Operation(summary = "Get sold books", description = "Get All Books vendor solds")
    @GetMapping("sold-books")
    public List<BookResponse> bookSold(Authentication authentication) {
        return bookService.getBooksSold(authentication.getName(), ERole.ROLE_VENDOR);
    }

    @Operation(summary = "Get books in progress", description = "Get a books in progress")
    @GetMapping("books-in-process/{offset}")
    public List<Book> getBooksInProcess(@PathVariable Integer offset, Authentication authentication) {
        return bookService.findBooksFromVendorInProcess(--offset, 12, authentication.getName(), RequestStatus.INPROGRESS);
    }

    @Operation(summary = "Get refused books", description = "Get refused books")
    @GetMapping("books-refused/{offset}")
    public List<Book> getBooksInCancel(@PathVariable Integer offset, Authentication authentication) {
        return bookService.findBooksFromVendorCancelled(--offset, 12, authentication.getName(), RequestStatus.REFUSED);
    }

    @Operation(summary = "Get all genres")
    @GetMapping("genres")
    public List<Genre> getAllGenres() {
        return bookService.getAllGenres();
    }

    @Operation(summary = "Get count of vendor books")
    @GetMapping("count-books")
    public CountForAdmin getCountOfVendorBooks(Authentication authentication) {
        return bookService.getCountOfVendorBooks(authentication.getName());
    }

    @Operation(summary = "Delete Vendor profile ")
    @DeleteMapping("profile")
    public ResponseEntity<?> deleteVendorProfile(Authentication authentication) {
        return vendorService.delete(authentication.getName());
    }

}