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
<<<<<<<< HEAD:src/main/java/kg/peaksoft/ebookb4/api/RegisterApi.java
public class RegisterApi {
========
public class SignUpApi {
>>>>>>>> 76e5676a0ab8edb64a4d8b8577ed29958ec6c879:src/main/java/kg/peaksoft/ebookb4/api/auth/SignUpApi.java
    private final ClientService clientService;
    private  final VendorService vendorService;

    @Operation(summary = "singup", description = "registered client")
    @PostMapping("/signup/client")
    public ResponseEntity<?> registerClient(@RequestBody @Valid SignupRequestClient client) {
        return clientService.register(client, 1L);
    }

    @Operation(summary = "signup", description = "registreted vendor")
    @PostMapping("/signup/vendor")
    public ResponseEntity<?> registerVendor(@Valid @RequestBody SignupRequestVendor vendor) {
        return vendorService.register(vendor, 2L);
    }

}
