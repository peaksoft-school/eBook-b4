package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.booksClasses.Basket;
import kg.peaksoft.ebookb4.db.repository.BasketRepository;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.dto.dto.users.ClientRegisterDTO;
import kg.peaksoft.ebookb4.dto.dto.users.ClientUpdateDTO;
import kg.peaksoft.ebookb4.dto.mapper.ClientRegisterMapper;
import kg.peaksoft.ebookb4.dto.response.MessageResponse;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.repository.RoleRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import kg.peaksoft.ebookb4.db.service.ClientService;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

import static kg.peaksoft.ebookb4.db.models.enums.RequestStatus.ACCEPTED;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final BookRepository bookRepository;
    private final BasketRepository basketRepository;
    ClientRegisterMapper clientRegisterMapper;

    @Override
    public ResponseEntity<?> register(ClientRegisterDTO clientRegisterDTO, Long number) {
        //checking if passwords are the same or not
        if (!clientRegisterDTO.getPassword().equals(clientRegisterDTO.getConfirmPassword())) {
            throw new BadRequestException("Passwords are not the same!");
        }

        if (userRepository.existsByEmail(clientRegisterDTO.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        User user = new User(
                clientRegisterDTO.getEmail(),
                encoder.encode(clientRegisterDTO.getPassword()));
        user.setFirstName(clientRegisterDTO.getFirstName());
        user.setRole(roleRepository.getById(number));
        user.setLastName("");
        user.setNumber("");
        user.setDateOfRegistration(LocalDate.now());
        Basket basket1 = new Basket();
        basket1.setUser(user);
        user.setBasket(basket1);
        userRepository.save(user);


        return ResponseEntity.ok(new MessageResponse(
                String.format("User with email %s registered successfully!", user.getEmail().toUpperCase(Locale.ROOT))));
    }

    @Override
    @Transactional
    public ResponseEntity<?> likeABook(Long bookId, String username) {

        User user = userRepository.getUser(username).orElseThrow(() ->
                new BadRequestException("User doesn't exist!"));
        Book book = bookRepository.getById(bookId);
        if (book.getRequestStatus() != ACCEPTED) {
            throw new BadRequestException("This book has not been went through admin-check!");
        }
        if (bookRepository.checkIfAlreadyPutLike(bookId, user.getId()) > 0) {
            throw new BadRequestException("You already put like to this book!");
        }
        if(bookRepository.getById(bookId).getRequestStatus()==ACCEPTED){
            user.getLikedBooks().add(book);
            bookRepository.incrementLikesOfBook(bookId);
        }
        else{
            throw new BadRequestException("This book has not went through admin-check yet!");
        }

        return ResponseEntity.ok(new MessageResponse(String.format(
                "Book with id %s successfully has been liked by user with name %s", bookId, username)));
    }

    @Override
    @Transactional
    public ResponseEntity<?> addBookToBasket(Long bookId, String username) {

        if (basketRepository.checkIfAlreadyClientPutInBasket(
                getUsersBasketId(username), bookId) > 0) {
            System.out.println("It has been checked!");
            throw new BadRequestException("You already put this book in your basket");
        }

        User user = userRepository.getUser(username).orElseThrow(() ->
                new BadRequestException(String.format("User with username %s not found", username)));

        if(bookRepository.getById(bookId).getRequestStatus()==ACCEPTED){
            user.getBasket().getBooks().add(bookRepository.getById(bookId));
            bookRepository.incrementBasketsOfBooks(bookId);
        }
        else{
            throw new BadRequestException("This book has not went through admin-check yet!");
        }

        return ResponseEntity.ok(new MessageResponse(String.format("Book with id %s has been added to basket of user" +
                "with username %s", bookId, username)));

    }

    @Override
    @Transactional
    public ResponseEntity<?> update(ClientUpdateDTO newClientDTO, String username) {
        User user = userRepository.getUser(username).orElseThrow(() ->
                new BadRequestException(String.format("User with username %s has not been found", username)));

        if (userRepository.existsByEmail(newClientDTO.getEmail())) {
            throw new BadRequestException(String.format("Please choose another email, %s email is not available", newClientDTO.getEmail()));
        }
        String oldEmail = user.getEmail();
        String newEmail = newClientDTO.getEmail();
        if (!oldEmail.equals(newEmail)) {
            user.setEmail(newEmail);
        }
        String oldFirstName = user.getFirstName();
        String newFirstName = newClientDTO.getFirstName();
        if (!oldFirstName.equals(newFirstName)) {
            user.setFirstName(newFirstName);
        }
        String oldPasswordOldUser = user.getPassword();

        String oldPasswordNewUser = newClientDTO.getOldPassword();
        String newPasswordNewUser = newClientDTO.getNewPassword();
        String newPasswordConfirmNewUser = newClientDTO.getConfirmNewPassword();
        if (encoder.matches(oldPasswordNewUser, oldPasswordOldUser)) {
            if (newPasswordNewUser.equals(newPasswordConfirmNewUser)) {
                user.setPassword(encoder.encode(newPasswordNewUser));
            } else {
                throw new BadRequestException("Your new password didn't match!");
            }
        } else {
            throw new BadRequestException("You wrote wrong old password!");
        }

        return ResponseEntity.ok("User have changed details");
    }

    @Override
    public ClientRegisterDTO getClientDetails(String username) {
        return clientRegisterMapper.createDTO(userRepository.getUser(username).orElseThrow(() ->
                new BadRequestException(String.format("User with username %s has not been found!", username))));
    }

    @Override
    @Transactional
        public ResponseEntity<?> deleteBookFromBasket(Long id,String email) {
        User user = userRepository.getUser(email).orElseThrow(()->
                new BadRequestException(String.format("User with id %s has not been found!",email)));
        Book book = bookRepository.getById(id);
        user.getBasket().getBooks().remove(book);
        return ResponseEntity.ok("Delete book from basket of "+email);
    }

    @Override
    @Transactional
    public ResponseEntity<?> cleanBasketOfClientByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(
                        "Client with email = " + email + " does not exists"
                ));
        user.getBasket().clear();
        return  ResponseEntity.ok("Clean books from basket of "+email);
    }

    public Long getUsersBasketId(String username) {
        return basketRepository.getUsersBasketId(username);
    }
}
