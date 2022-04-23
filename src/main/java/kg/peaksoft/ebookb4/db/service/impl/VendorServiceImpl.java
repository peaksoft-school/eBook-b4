package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.repository.RoleRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import kg.peaksoft.ebookb4.db.service.VendorService;
import kg.peaksoft.ebookb4.dto.request.SignupRequestVendor;
import kg.peaksoft.ebookb4.dto.response.MessageResponse;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<?> register(SignupRequestVendor signupRequestVendor, Long number) {

        if (!signupRequestVendor.getPassword().equals(signupRequestVendor.getConfirmPassword())) {
            throw new BadRequestException("Passwords are not the same !");
        }
        if (userRepository.existsByEmail(signupRequestVendor.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        User user = new User(signupRequestVendor.getEmail(),
                encoder.encode(signupRequestVendor.getPassword()));
        user.setFirstName(signupRequestVendor.getFirstName());
        user.setLastName(signupRequestVendor.getLastName());
        user.setNumber(signupRequestVendor.getNumber());
        user.setRole(roleRepository.getById(number));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse(
                String.format("User with email %s registered successfully!", user.getEmail().toUpperCase(Locale.ROOT))));
    }

    @Override
    @Transactional
    public SignupRequestVendor update(SignupRequestVendor newUser, Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(
                        "vendor with id = " + id + " does not exists"));

        if (!newUser.getEmail().equals(user.getEmail())) {
            Optional<User> clientOptional = userRepository.getUser(newUser.getEmail());

            if (clientOptional.isPresent()) {
                throw new BadRequestException(
                        "client with email = " + newUser.getEmail() + " has already exists"
                );
            }
        }

        String currentPassword = user.getPassword();
        String currentPassword2 = encoder.encode(newUser.getPassword());
        System.out.println(currentPassword);
        System.out.println(currentPassword2);

        boolean matches = encoder.matches(newUser.getPassword(), user.getPassword());

        if (!matches) {
            throw new BadRequestException(
                    "invalid current password "
            );
        }

        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setNumber(newUser.getNumber());
        user.setPassword(encoder.encode(newUser.getConfirmPassword()));

        return newUser;
    }
}