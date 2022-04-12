package kg.peaksoft.ebookb4.api;


import kg.peaksoft.ebookb4.models.userClasses.ERole;
import kg.peaksoft.ebookb4.models.userClasses.Role;
import kg.peaksoft.ebookb4.models.userClasses.User;
import kg.peaksoft.ebookb4.payload.request.LoginRequest;
import kg.peaksoft.ebookb4.payload.request.SignupRequest;
import kg.peaksoft.ebookb4.payload.response.JwtResponse;
import kg.peaksoft.ebookb4.payload.response.MessageResponse;
import kg.peaksoft.ebookb4.repository.RoleRepository;
import kg.peaksoft.ebookb4.repository.UserRepository;
import kg.peaksoft.ebookb4.security.jwt.JwtUtils;
import kg.peaksoft.ebookb4.security.services.UserDetailsImpl;
import kg.peaksoft.ebookb4.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static kg.peaksoft.ebookb4.models.userClasses.ERole.ROLE_CLIENT;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
  AuthenticationManager authenticationManager;
  JwtUtils jwtUtils;
  UserService userService;

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

  @PostMapping("/signup/client")
  public ResponseEntity<?> registerClient(@Valid @RequestBody SignupRequest signUpRequest) {
    return userService.register(signUpRequest, 1L);
  }

  @PostMapping("/signup/vendor")
  public ResponseEntity<?> registerVendor(@Valid @RequestBody SignupRequest signUpRequest) {
    return userService.register(signUpRequest, 2L);
  }

}
