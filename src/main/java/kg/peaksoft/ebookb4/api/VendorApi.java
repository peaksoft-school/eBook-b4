package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import kg.peaksoft.ebookb4.db.service.BookService;
import kg.peaksoft.ebookb4.db.service.PromoService;
import kg.peaksoft.ebookb4.db.service.VendorService;
import kg.peaksoft.ebookb4.dto.dto.PromoRequest;
import kg.peaksoft.ebookb4.dto.dto.VendorDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    private UserRepository userRepository;

    @Operation(summary = "promo", description = "Vendor creates a promo")
    @PostMapping("/create-promo")
    public ResponseEntity<?> createPromo(@RequestBody PromoRequest promoRequest, Authentication authentication){
        return promoService.createPromo(promoRequest, authentication.getName());
    }


//    public ResponseEntity<?> getVendorDetails(Authentication authentication){
//
//    }
//
//    public ResponseEntity<?> updateVendor(@RequestBody VendorDTO vendor){
//
//    }

//    @Operation(summary = "Update Vendor By Id" ,description = "Update")
//    @PatchMapping("/updateById")
//    public ResponseEntity<?> updateVendorById( SignupRequestVendor signupRequestVendor){
//        String user = SecurityContextHolder.getContext().getAuthentication().getName();
//        return vendorService.update(signupRequestVendor,user);
//    }


}
