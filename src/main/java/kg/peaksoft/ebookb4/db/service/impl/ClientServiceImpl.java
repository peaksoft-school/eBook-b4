package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.userClasses.Role;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.dto.request.SignupRequestClient;
import kg.peaksoft.ebookb4.dto.response.MessageResponse;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.repository.RoleRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import kg.peaksoft.ebookb4.db.service.ClientService;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final BookRepository bookRepository;

    @Override
    public ResponseEntity<?> register( SignupRequestClient signupRequestClient, Long number) {

        //checking if passwords are the same or not
        if(!signupRequestClient.getPassword().equals(signupRequestClient.getConfirmPassword())){
            throw new BadRequestException("Passwords are not the same!");
        }

        if (userRepository.existsByEmail(signupRequestClient.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        User user = new User(
                signupRequestClient.getEmail(),
                encoder.encode(signupRequestClient.getPassword()));
        user.setFirstName(signupRequestClient.getFirstName());
        user.setRole(roleRepository.getById(number));
        user.setLastName("");
        user.setNumber("");
            userRepository.save(user);


        return ResponseEntity.ok(new MessageResponse(
                String.format("User with email %s registered successfully!", user.getEmail().toUpperCase(Locale.ROOT))));
    }

    @Override
    public ResponseEntity<?> likeABook(Long bookId, String username) {

        User user = userRepository.getUser(username).orElseThrow(()->
                new BadRequestException("User doesn't exist!"));
        Book book = bookRepository.getById(bookId);
        if(!book.getIsActive()){
            throw new BadRequestException("This book has not been went through admin-check!");
        }
        if(bookRepository.checkIfAlreadyPutLike(bookId, user.getId())>0){
            throw new BadRequestException("You already put like to this book!");
        }
        user.getLikedBooks().add(book);
        bookRepository.incrementLikesOfBook(bookId);
        return ResponseEntity.ok(new MessageResponse(String.format(
                "Book with id %s successfully has been liked by user with name %s",bookId,username)));
    }

//
//    public SignupRequestClient  updateClient(Long id, SignupRequestClient clientDto) {
//
//        User clientFromDatabase = userRepository.findById(id)
//                .orElseThrow(() -> new BadRequestException(
//                        "client with id = " + id + " does not exists"
//                ));
//        if (!clientDto.getEmail().equals(clientFromDatabase.getEmail())) {
//            Optional<User> clientOptional = userRepository.getUser(clientDto.getEmail());
//
//            if (clientOptional.isPresent()) {
//                throw new BadRequestException(
//                        "client with email = " + clientDto.getEmail() + " has already exists"
//                );
//            }
//        }
//        String currentPassword = clientFromDatabase.getPassword();
//        String currentPassword2 = encoder.encode(clientDto.getPassword());
//        System.out.println(currentPassword);
//        System.out.println(currentPassword2);
//
//        boolean matches = encoder.matches(clientDto.getConfirmPassword(), clientFromDatabase.getPassword());
//
//        if (!matches) {
//            throw new BadRequestException(
//                    "invalid current password "
//            );
//        }
//
//        clientFromDatabase.setFirstName(clientDto.getFirstName());
//        clientFromDatabase.setEmail(clientDto.getEmail());
//        clientFromDatabase.setPassword(encoder.encode(clientDto.getPassword()));
//
//        return map.map(clientFromDatabase, ClientDto.class);
//    }

}
