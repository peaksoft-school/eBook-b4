package kg.peaksoft.ebookb4.api;

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

@CrossOrigin
@RestController
@RequestMapping("/api/vendor")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_VENDOR')")
@Tag(name = "Vendor",description = "Vendor accessible apis")
public class VendorApi {

    private PromoService promoService;
    private VendorService vendorService;

    @Operation(summary = "promo", description = "Vendor creates a promo")
    @PostMapping("/create-promo")
    public ResponseEntity<?> createPromo(@RequestBody PromoRequest promoRequest, Authentication authentication){
        return promoService.createPromo(promoRequest, authentication.getName());
    }

    @GetMapping("/getDetails")
    public VendorRegisterDTO getVendorDetails(Authentication authentication){
        return vendorService.getVendorDetails(authentication.getName());
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateVendor(@RequestBody VendorUpdateDTO newVendorDTO, Authentication authentication){
        return vendorService.update(newVendorDTO, authentication.getName());
    }



}
