package kg.peaksoft.ebookb4.api.admin;

import io.swagger.v3.oas.annotations.Operation;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.response.VendorResponse;
import kg.peaksoft.ebookb4.db.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/admin/vendor")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class VendorAdminApi {

    private AdminService service;


    @Operation(summary = "Get all Vendors",
            description = "Get all vendors with amount of books")
    @GetMapping("/")
    public List<VendorResponse> getAllVendors() {
        return service.findAllVendors();
    }

    @Operation(summary = "Get vendor by id")
    @GetMapping("/{id}")
    public VendorResponse getByVendorId(@PathVariable Long id) {
        return service.getVendor(id);
    }

    @Operation(summary = "Get all books of vendor in admin panel",
            description = "Get all books by vendor id")
    @GetMapping("/{vendorId}/books/{offset}")
    public List<Book> getBooksOfVendor(@PathVariable Long vendorId,
                                       @PathVariable Integer offset) {
        return service.findBooksFromVendor(--offset, 12, vendorId);
    }


    @Operation(summary = "Get books with likes in admin panel",
            description = "Get books with at least one like")
    @GetMapping("/{vendorId}/favorite-books/{offset}")
    public List<Book> getLikedBooks(@PathVariable Long vendorId, @PathVariable Integer offset) {
        return service.findBooksFromVendorInFavorites(--offset, 12, vendorId);
    }

    @Operation(summary = "Get books in basket in admin panel",
            description = "Get books added to basket at least once")
    @GetMapping("/{vendorId}/basket-books/{offset}")
    public List<Book> getBooksAddedToBasket(@PathVariable Long vendorId,
                                            @PathVariable Integer offset) {
        return service.findBooksFromVendorAddedToBasket(--offset, 12, vendorId);
    }

    @Operation(summary = "Get books with discount in admin panel",
            description = "Get books with discount")
    @GetMapping("/{vendorId}/books-discount/{offset}")
    public List<Book> getBooksWithDiscount(@PathVariable Long vendorId
            , @PathVariable Integer offset) {
        return service.findBooksFromVendorWithDiscount(--offset, 12, vendorId);
    }

    @Operation(summary = "Delete client/vendor ",
            description = "Admin can delete client and vendor!")
    @DeleteMapping({"/removeUser/{id}"})
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        return service.deleteById(id);
    }


}
