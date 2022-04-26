package kg.peaksoft.ebookb4.api.Vendor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.service.PromoService;
import kg.peaksoft.ebookb4.db.service.VendorService;
import kg.peaksoft.ebookb4.dto.dto.others.PromoRequest;
import kg.peaksoft.ebookb4.dto.dto.users.VendorRegisterDTO;
import kg.peaksoft.ebookb4.dto.dto.users.VendorUpdateDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/vendor")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_VENDOR')")
@Tag(name = "Vendor",description = "Vendor accessible apis")
public class VendorApi {

    private PromoService promoService;
    private VendorService vendorService;

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



}
