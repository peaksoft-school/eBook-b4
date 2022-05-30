package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.dto.BookDTO;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.entity.Genre;
import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.db.models.response.BookResponse;
import kg.peaksoft.ebookb4.db.models.response.BookResponseAfterSaved;
import kg.peaksoft.ebookb4.db.service.BookService;
import kg.peaksoft.ebookb4.db.service.PromoService;
import kg.peaksoft.ebookb4.db.service.VendorService;
import kg.peaksoft.ebookb4.db.models.request.PromoRequest;
import kg.peaksoft.ebookb4.db.models.dto.VendorRegisterDTO;
import kg.peaksoft.ebookb4.db.models.dto.VendorUpdateDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/vendor")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_VENDOR')")
@Tag(name = "Vendor",description = "Vendor accessible apis")
public class VendorApi {

    private PromoService promoService;
    private VendorService vendorService;
    private BookService bookService;

    @Operation(summary = "Promo", description = "Vendor creates a promo")
    @PostMapping("/promo")
    public ResponseEntity<?> createPromo(@RequestBody PromoRequest promoRequest, Authentication authentication){
        return promoService.createPromo(promoRequest, authentication.getName());
    }

    @Operation(summary = "Vendor profile", description = "All accessible data of client")
    @GetMapping("/profile")
    public VendorRegisterDTO getVendorDetails(Authentication authentication){
        return vendorService.getVendorDetails(authentication.getName());
    }

    @Operation(summary = "Update vendor", description = "Updating vendor profile")
    @PatchMapping("/profile/settings")
    public ResponseEntity<?> updateVendor(@RequestBody VendorUpdateDTO newVendorDTO, Authentication authentication){
        return vendorService.update(newVendorDTO, authentication.getName());
    }


    @Operation(summary = "Save book",description = "Adding a new book")
    @PostMapping("/new-book")
    public BookResponseAfterSaved saveBook(@RequestBody BookDTO bookDTO, Authentication authentication){
        return bookService.saveBook(bookDTO, authentication.getName());
    }

    @Operation(summary = "Delete book",description = "Deleting a book by id")
    @DeleteMapping("/remove/{bookId}")
    public ResponseEntity<?> delete(@PathVariable Long bookId) {
        return bookService.delete(bookId);
    }

    @Operation(summary = "Update book",description = "update a book")
    @PatchMapping("/editing/{bookId}")
    public ResponseEntity<?> update(@RequestBody BookDTO request,
                                    @PathVariable Long  bookId){
        Long genreId = request.getGenreId();
        return bookService.update(request, bookId ,genreId);
    }

    @Operation(summary = "Get book",
            description = "Get book by id for vendor")
    @GetMapping("/book/{bookId}")
    public Book getBookById(@PathVariable Long bookId){
        return bookService.findByBookId(bookId);
    }

    @Operation(summary = "Get all books for vendor",
            description = "Get all books by token")
    @GetMapping("/books/{offset}")
    public List<Book> getBooksOfVendor(@PathVariable Integer offset, Authentication authentication){
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

    @Operation(summary = "Get books with discount", description = "Get books with discount")
    @GetMapping("/books-discount/{offset}")
    public List<Book> getBooksWithDiscount(@PathVariable Integer offset, Authentication authentication){
        return bookService.findBooksFromVendorWithDiscount(--offset, 12, authentication.getName());
    }

    @Operation(summary = "Get sold books",description = "Get All Books vendor solds")
    @GetMapping("/sold-books")
    public List<BookResponse> bookSold(Authentication authentication){
        return bookService.getBooksSold(authentication.getName(), ERole.ROLE_VENDOR);
    }

    @Operation(summary = "Get books in progress",
            description = "Get a books in progress")
    @GetMapping("/books-in-process/{offset}")
    public List<Book> getBooksInProcess(@PathVariable Integer offset, Authentication authentication){
        return bookService.findBooksFromVendorInProcess(--offset, 12, authentication.getName(),
                RequestStatus.INPROGRESS);
    }

    @Operation(summary = "Get refused books",
            description = "Get refused books")
    @GetMapping("/books-refused/{offset}")
    public List<Book> getBooksInCancel(@PathVariable Integer offset, Authentication authentication){
        return bookService.findBooksFromVendorCancelled(--offset, 12, authentication.getName(),
                RequestStatus.REFUSED);
    }
    @Operation(summary = "Get all genres")
    @GetMapping("/get-all-genres")
    public List<Genre> getAllGenres(){
        return bookService.getAllGenres();
    }
}