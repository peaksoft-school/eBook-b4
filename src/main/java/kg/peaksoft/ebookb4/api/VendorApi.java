package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.service.BookService;
import kg.peaksoft.ebookb4.db.service.PromoService;
import kg.peaksoft.ebookb4.dto.request.PromoRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Operation(summary = "promo", description = "Vendor creates a promo")
    @PostMapping("/create-promo")
    public ResponseEntity<?> createPromo(@RequestBody PromoRequest promoRequest){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return promoService.createPromo(promoRequest,username);
    }



}
