package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.repository.BasketRepository;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.dto.request.SignupRequestClient;
import kg.peaksoft.ebookb4.dto.response.MessageResponse;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.repository.RoleRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import kg.peaksoft.ebookb4.db.service.ClientService;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final BookRepository bookRepository;
    private final BasketRepository basketRepository;

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
    @Transactional
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

    @Override
    @Transactional
    public ResponseEntity<?> addBookToBasket(Long bookId, String username) {

        if(basketRepository.checkIfAlreadyClientPutInBasket(
                getUsersBasketId(username),bookId)>0){
            System.out.println("It has been checked!");
            throw new BadRequestException("You already put this book in your basket");
        }

        User user = userRepository.getUser(username).orElseThrow(()->
                new BadRequestException(String.format("User with username %s not found", username)));

        user.getBasket().getBooks().add(bookRepository.getById(bookId));
        bookRepository.incrementBasketsOfBooks(bookId);
        return ResponseEntity.ok(new MessageResponse(String.format("Book with id %s has been added to basket of user" +
                "with username %s",bookId,username)));

    }

    public Long getUsersBasketId(String username){
        return basketRepository.getUsersBasketId(username);
    }




}
