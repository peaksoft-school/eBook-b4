package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import kg.peaksoft.ebookb4.db.service.BookService;
import kg.peaksoft.ebookb4.db.service.VendorService;
import kg.peaksoft.ebookb4.dto.response.VendorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Tag(name = "Admin",description = "Admin accessible apis")
public class AdminApi {
    private BookService bookService;
    private VendorService vendorService;
    private UserRepository userRepository;

    @Operation(summary = "Get all Vendors")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getVendors")
    public List<VendorResponse> getAllVendors(){
       return vendorService.findAllVendors();
    }

    @DeleteMapping({"/deleteById/{id}"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete vendor", description = "Delete vendor by id ")
    public ResponseEntity<Void> deleteVendorById(@PathVariable("id") Long id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.ok().build();
    }
}
