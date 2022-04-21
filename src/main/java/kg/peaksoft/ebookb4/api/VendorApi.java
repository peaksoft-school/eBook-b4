package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.bookClasses.Book;
import kg.peaksoft.ebookb4.db.service.BookService;
import kg.peaksoft.ebookb4.db.service.PromoService;
import kg.peaksoft.ebookb4.dto.request.PromoRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/vendors")
@PreAuthorize("hasRole('ROLE_VENDOR')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Vendor API", description = "Vendor accessible apis")
public class VendorApi {

    private PromoService promoService;
    private BookService bookService;

    @Operation(summary = "Promo", description = "Vendor creates a promo")
    @PostMapping("/create-promo")
    public ResponseEntity<?> createPromo(@RequestBody PromoRequest promoRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return promoService.createPromo(promoRequest, username);
    }

    @Operation(summary = "Vendor books", description = "Find all vendor books")
    @GetMapping("/books")
    public List<Book> findAllBookFromVendor() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return bookService.findBooksFromVendor(username);
    }
}
