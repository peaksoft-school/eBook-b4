package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import kg.peaksoft.ebookb4.db.service.BookService;
import kg.peaksoft.ebookb4.db.service.PromoService;
import kg.peaksoft.ebookb4.db.service.VendorService;
import kg.peaksoft.ebookb4.dto.request.PromoRequest;
import kg.peaksoft.ebookb4.dto.request.SignupRequestVendor;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/vendor")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_VENDOR')")
@Tag(name = "Vendor",description = "Vendor accessible apis")
public class VendorApi {

    private BookService bookService;
    private PromoService promoService;
    private VendorService vendorService;

    @Operation(summary = "promo", description = "Vendor creates a promo")
    @PostMapping("/create-promo")
    public ResponseEntity<?> createPromo(@RequestBody PromoRequest promoRequest,
                                         Authentication authentication){
        return promoService.createPromo(promoRequest, authentication.getName());
    }

    @Operation(summary = "Find Books From Vendor")
    @GetMapping
    public List<Book> findAllBookFromVendor(Integer offset){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return bookService.findBooksFromVendor(offset,1, username);
    }

    @Operation(summary = "Update Vendor By Id" ,description = "Update")
    @PatchMapping("/{id}")
    public SignupRequestVendor updateVendorById( SignupRequestVendor signupRequestVendor,
                                                 @PathVariable Long id){
        return vendorService.update(signupRequestVendor,id);
    }
}
