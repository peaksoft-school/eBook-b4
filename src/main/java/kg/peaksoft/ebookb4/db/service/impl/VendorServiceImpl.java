package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.repository.RoleRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import kg.peaksoft.ebookb4.db.service.VendorService;
import kg.peaksoft.ebookb4.dto.dto.users.VendorRegisterDTO;
import kg.peaksoft.ebookb4.dto.dto.users.VendorUpdateDTO;
import kg.peaksoft.ebookb4.dto.mapper.VendorRegisterMapper;
import kg.peaksoft.ebookb4.dto.response.MessageResponse;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Locale;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private VendorRegisterMapper vendorRegisterMapper;

    @Override
    public ResponseEntity<?> register(VendorRegisterDTO vendorDTO, Long number) {

        if (!vendorDTO.getPassword().equals(vendorDTO.getConfirmPassword())) {
            throw new BadRequestException("Passwords are not the same !");
        }
        if (userRepository.existsByEmail(vendorDTO.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        User user = new User(vendorDTO.getEmail(),
                encoder.encode(vendorDTO.getPassword()));
        user.setFirstName(vendorDTO.getFirstName());
        user.setLastName(vendorDTO.getLastName());
        user.setNumber(vendorDTO.getNumber());
        user.setRole(roleRepository.getById(number));

        user.setDateOfRegistration(LocalDate.now());

        userRepository.save(user);
        log.info("Method save vendor works");
        return ResponseEntity.ok(new MessageResponse(
                String.format("User with email %s registered successfully!", user.getEmail().toUpperCase(Locale.ROOT))));
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(VendorUpdateDTO newVendorDTO, String username) {
        User user = userRepository.getUser(username).orElseThrow(() ->
                new BadRequestException(String.format("User with username %s has not been found", username)));

//        if (userRepository.existsByEmail(newVendorDTO.getEmail())) {
//            throw new BadRequestException(String.format("Please choose another email, %s email is not available", newVendorDTO.getEmail()));
//        }
//        String oldEmail = user.getEmail();
//        String newEmail = newVendorDTO.getEmail();
//        if (!oldEmail.equals(newEmail)) {
//            user.setEmail(newEmail);
//        }
        String oldFirstName = user.getFirstName();
        String newFirstName = newVendorDTO.getFirstName();
        if (!oldFirstName.equals(newFirstName)) {
            user.setFirstName(newFirstName);
        }
        String oldLastName = user.getLastName();
        String newLastName = newVendorDTO.getLastName();
        if (oldLastName.equals(newLastName)) {
            user.setLastName(newLastName);
        }
        String oldNumber = user.getNumber();
        String newNumber = newVendorDTO.getNumber();
        if (!oldNumber.equals(newNumber)) {
            user.setNumber(newNumber);
        }

        String oldPasswordOldUser = user.getPassword();

        String oldPasswordNewUser = newVendorDTO.getOldPassword();
        String newPasswordNewUser = newVendorDTO.getNewPassword();
        String newPasswordConfirmNewUser = newVendorDTO.getConfirmNewPassword();
        if (encoder.matches(oldPasswordNewUser, oldPasswordOldUser)) {
            if (newPasswordNewUser.equals(newPasswordConfirmNewUser)) {
                user.setPassword(encoder.encode(newPasswordNewUser));
            } else {
                throw new BadRequestException("Your new password didn't match!");
            }
        } else {
            throw new BadRequestException("You wrote wrong old password!");
        }
        log.info("Update vendor works");
        return ResponseEntity.ok("User have changed details");
    }

    public VendorRegisterDTO getVendorDetails(String username) {
        log.info("Get vendor details");
        return vendorRegisterMapper.createDTO(userRepository.getUser(username).orElseThrow(() ->
                new BadRequestException(String.format("User with username %s has not been found!", username))));
    }
}