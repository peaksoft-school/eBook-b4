package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.repository.RoleRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import kg.peaksoft.ebookb4.db.service.VendorService;
import kg.peaksoft.ebookb4.dto.mapper.VendorMapper;
import kg.peaksoft.ebookb4.dto.request.SignupRequestVendor;
import kg.peaksoft.ebookb4.dto.response.MessageResponse;
import kg.peaksoft.ebookb4.dto.response.VendorResponse;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class VendorServiceImpl implements VendorService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final VendorMapper vendorMapper;

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
    public List<VendorResponse> findAllVendors() {
        List<User> users  = userRepository.findAllVendors(ERole.ROLE_VENDOR);
        List<VendorResponse> vendorResponses = new ArrayList<>();
        for(User i : users){
            vendorResponses.add(vendorMapper.createVendorDto(i));
        }
        return vendorResponses;

    }

    @Override
    public void deleteVendor(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BadRequestException(
                    "Client with id " + id + " does not exists");
        }
        userRepository.deleteById(id);
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