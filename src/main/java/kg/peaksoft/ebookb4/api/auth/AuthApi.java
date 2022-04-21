package kg.peaksoft.ebookb4.api.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.dto.request.LoginRequest;
import kg.peaksoft.ebookb4.dto.request.SignupRequestClient;
import kg.peaksoft.ebookb4.dto.request.SignupRequestVendor;
import kg.peaksoft.ebookb4.dto.response.JwtResponse;
import kg.peaksoft.ebookb4.config.jwt.JwtUtils;
import kg.peaksoft.ebookb4.config.services.UserDetailsImpl;
import kg.peaksoft.ebookb4.db.service.ClientService;
import kg.peaksoft.ebookb4.db.service.VendorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/public")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Authentication API", description = "Authorization with jwt token")
public class AuthApi {

    AuthenticationManager authenticationManager;
    JwtUtils jwtUtils;
    VendorService vendorService;
    ClientService clientService;

    @Operation(summary = "Sign In", description = "Sign In Users")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @Operation(summary = "Sign Up client", description = "Register a Client")
    @PostMapping("/signup/client")
    public ResponseEntity<?> registerClient(@Valid @RequestBody SignupRequestClient client) {
        return clientService.register(client, 1L);
    }

    @Operation(summary = "Sign Up Vendor", description = "Register a Vendor")
    @PostMapping("/signup/vendor")
    public ResponseEntity<?> registerVendor(@Valid @RequestBody SignupRequestVendor vendor) {
        return vendorService.register(vendor, 2L);
    }




}
