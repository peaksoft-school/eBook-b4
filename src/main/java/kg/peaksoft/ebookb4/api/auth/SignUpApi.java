package kg.peaksoft.ebookb4.api.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.service.ClientService;
import kg.peaksoft.ebookb4.db.service.VendorService;
import kg.peaksoft.ebookb4.dto.request.SignupRequestClient;
import kg.peaksoft.ebookb4.dto.request.SignupRequestVendor;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Tag(name = "AuthController",description = "singup user and  vendor")
public class SignUpApi {
    private final ClientService clientService;
    private  final VendorService vendorService;

    @Operation(summary = "singup", description = "register a client")
    @PostMapping("/signup/client")
    public ResponseEntity<?> registerClient(@Valid @RequestBody SignupRequestClient client) {
        return clientService.register(client, 1L);
    }

    @Operation(summary = "signup", description = "registr a vendor")
    @PostMapping("/signup/vendor")
    public ResponseEntity<?> registerVendor(@Valid @RequestBody SignupRequestVendor vendor) {
        return vendorService.register(vendor, 2L);
    }

}
