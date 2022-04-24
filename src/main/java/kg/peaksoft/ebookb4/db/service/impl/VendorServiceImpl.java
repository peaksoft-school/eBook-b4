package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.repository.RoleRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import kg.peaksoft.ebookb4.db.service.VendorService;
import kg.peaksoft.ebookb4.dto.dto.VendorDTO;
import kg.peaksoft.ebookb4.dto.response.MessageResponse;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<?> register(VendorDTO vendorDTO, Long number) {

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

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse(
                String.format("User with email %s registered successfully!", user.getEmail().toUpperCase(Locale.ROOT))));
    }

//    @Override
//    @Transactional
//    public ResponseEntity<?> update(SignupRequestVendor newUser, String name,Long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new BadRequestException(
//                    "vendor with id = " + id + " does not exists"
//            ));
//
//        Optional<User> user2 = userRepository.getUser(name);
//        if (user.isPresent()) {
//            throw new BadRequestException(
//                    "vendor with email = " + newUser.getEmail() + " has already exists"
//            );
//        user.setFirstName(newUser.getFirstName());
//        user.setLastName(newUser.getLastName());
//        user.setEmail(newUser.getEmail());
//        user.setNumber(newUser.getNumber());
//
//        String password = user.getPassword();
//        String newUserOldPassword = newUser.getPassword();
//
//        if (password.equals(newUserOldPassword)) {
//            String newPass = newUser.getNewPassword();
//            String newPassConfirm = newUser.getConfirmPassword();
//            if (newPass.equals(newPassConfirm)) {
//                user.setPassword(newPass);
//            }
//        }
//        return ResponseEntity.ok(new MessageResponse(
//                "Vendor with email  Update successfully! "));
//    }
//
//    public ResponseEntity<?> update(SignupRequestVendor newUser, Long id){
//    Vendor vendorFromDataBase = vendorRepository.findById(id)
//            .orElseThrow(() -> new DoesNotExistsException(
//                    "vendor with id = " + id + " does not exists"
//            ));
//    Optional<Vendor> optionalVendor = vendorRepository.findUserByEmail(vendorDto.getEmail());
//        if (optionalVendor.isPresent()) {
//        throw new AlreadyExistsException(
//                "vendor with email = " + vendorDto.getEmail() + " has already exists"
//        );
//    }
//        vendorFromDataBase.setFirstName(vendorDto.getFirstName());
//        vendorFromDataBase.setLastName(vendorDto.getLastName());
//        vendorFromDataBase.setPhoneNumber(vendorDto.getPhoneNumber());
//        vendorFromDataBase.setEmail(vendorDto.getEmail());
//        vendorFromDataBase.getAuthenticationInfo().setPassword((vendorDto.getPassword()));

}