package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import kg.peaksoft.ebookb4.dto.auth.AuthRequest;
import kg.peaksoft.ebookb4.dto.auth.AuthResponse;
import kg.peaksoft.ebookb4.models.UserAuth;
import kg.peaksoft.ebookb4.models.enums.Role;
import kg.peaksoft.ebookb4.repository.UserRepository;
import kg.peaksoft.ebookb4.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
@RestController
@RequestMapping("/api/authenticate")
@AllArgsConstructor
public class LoginApi {


    private final UserServiceImpl userService;

    @Operation(summary = "Get Token",description = "write email,password")
    @PostMapping
    public AuthResponse authenticate(@RequestBody AuthRequest authRequest) {
        return userService.authenticate(authRequest);
    }



}
