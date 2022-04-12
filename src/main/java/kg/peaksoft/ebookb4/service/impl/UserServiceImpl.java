package kg.peaksoft.ebookb4.service.impl;

import kg.peaksoft.ebookb4.config.JwtUtils;
import kg.peaksoft.ebookb4.dto.auth.AuthRequest;
import kg.peaksoft.ebookb4.dto.auth.AuthResponse;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import kg.peaksoft.ebookb4.exceptions.NotFoundException;
import kg.peaksoft.ebookb4.models.UserAuth;
import kg.peaksoft.ebookb4.models.enums.Role;
import kg.peaksoft.ebookb4.repository.UserRepository;
import kg.peaksoft.ebookb4.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {


    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //If user exists it return email, role and token!
    public AuthResponse authenticate(AuthRequest authRequest) {
        userRepository.findByEmail(authRequest.getEmail()).
                orElseThrow(() -> new BadRequestException
                        (String.format("User with %s email not found!",authRequest.getEmail())));

        Authentication authentication;
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(),
                    authRequest.getPassword()
            ));

            userRepository.findByEmail(authRequest.getEmail()).
                    orElseThrow(() -> new NotFoundException
                            (String.format("User with %s email not found!",authRequest.getEmail())));

        String generatedToken = jwtUtils.generateToken(authentication);
        return AuthResponse.builder()
                .email(authRequest.getEmail())
                .role(userRepository.findByEmail(authRequest.getEmail()).get().getRole().name())
                .token(generatedToken)
                .build();
    }
    @PostConstruct
    public void init(){
        UserAuth user = new UserAuth();
        user.setEmail("kutubekutush@gmail.com");
        user.setPassword(passwordEncoder.encode("kutubek"));
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }

}
