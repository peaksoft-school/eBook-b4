package kg.peaksoft.ebookb4.service.impl;

import kg.peaksoft.ebookb4.models.userClasses.User;
import kg.peaksoft.ebookb4.dto.request.SignupRequestVendor;
import kg.peaksoft.ebookb4.dto.response.MessageResponse;
import kg.peaksoft.ebookb4.repository.RoleRepository;
import kg.peaksoft.ebookb4.repository.UserRepository;
import kg.peaksoft.ebookb4.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<?> register(SignupRequestVendor signupRequestVendor, Long number) {

        if (userRepository.existsByEmail(signupRequestVendor.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        User user;
        if (number==1L){
            user = new User(
                    signupRequestVendor.getEmail(),
                    encoder.encode(signupRequestVendor.getPassword()));
            user.setFirstName(signupRequestVendor.getFirstName());
            user.setLastName("");
            user.setNumber("");
            user.setRole(roleRepository.getById(number));
            userRepository.save(user);
        }
        else{
            user = new User(
                    signupRequestVendor.getEmail(),
                    encoder.encode(signupRequestVendor.getPassword()));
            user.setFirstName(signupRequestVendor.getFirstName());
            user.setLastName(signupRequestVendor.getLastName());
            user.setNumber(signupRequestVendor.getNumber());
            user.setRole(roleRepository.getById(number));
            userRepository.save(user);
        }

        return ResponseEntity.ok(new MessageResponse(
                String.format("User with email %s registered successfully!",user.getEmail().toUpperCase(Locale.ROOT))));
    }

}
