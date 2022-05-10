package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.booksClasses.Basket;
import kg.peaksoft.ebookb4.db.models.booksClasses.ClientOperations;
import kg.peaksoft.ebookb4.db.models.booksClasses.Promocode;
import kg.peaksoft.ebookb4.db.models.dto.ClientOperationDTO;
import kg.peaksoft.ebookb4.db.models.dto.ClientRegisterDTO;
import kg.peaksoft.ebookb4.db.models.dto.ClientUpdateDTO;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.entity.User;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.response.CardOperationResponse;
import kg.peaksoft.ebookb4.db.models.mappers.ClientOperationMapper;
import kg.peaksoft.ebookb4.db.models.mappers.ClientRegisterMapper;
import kg.peaksoft.ebookb4.db.models.response.BookResponse;
import kg.peaksoft.ebookb4.db.models.response.CardResponse;
import kg.peaksoft.ebookb4.db.models.response.MessageResponse;
import kg.peaksoft.ebookb4.db.repository.*;
import kg.peaksoft.ebookb4.db.service.ClientService;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static kg.peaksoft.ebookb4.db.models.enums.RequestStatus.ACCEPTED;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final PromoRepo repo;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BasketRepository basketRepository;
    private final ClientRegisterMapper clientRegisterMapper;
    private final ClientOperationMapper clientOperationMapper;
    private final ClientOperationRepository clientOperationRepository;

    private final CardOperationResponse cardOperationResponse;

    @Override
    public ResponseEntity<?> register(ClientRegisterDTO clientRegisterDTO, Long number) {
        //checking if passwords are the same or not
        if (!clientRegisterDTO.getPassword().equals(clientRegisterDTO.getConfirmPassword())) {
            log.error("password are not the same ");
            throw new BadRequestException("Passwords are not the same!");
        }

        if (userRepository.existsByEmail(clientRegisterDTO.getEmail())) {
            log.error("Client with email = {} already in use", clientRegisterDTO.getEmail());
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        log.info("Saving new client {} to the database", clientRegisterDTO.getEmail());
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
        if (bookRepository.getById(bookId).getRequestStatus() == ACCEPTED) {
            user.getLikedBooks().add(book);
            bookRepository.incrementLikesOfBook(bookId);
        } else {
            throw new BadRequestException("This book has not went through admin-check yet!");
        }
        log.info("{} like to {}", user.getEmail(), book.getTitle());
        return ResponseEntity.ok(new MessageResponse(String.format(
                "Book with id %s successfully has been liked by user with name %s", bookId, username)));
    }

    @Override
    @Transactional
    public ResponseEntity<?> addBookToBasket(Long bookId, String username) {

        if (basketRepository.checkIfAlreadyClientPutInBasket(
                getUsersBasketId(username), bookId) > 0) {
            log.error("with book already to basket!");
            throw new BadRequestException("You already put this book in your basket");
        }

        User user = userRepository.getUser(username).orElseThrow(() ->
                new BadRequestException(String.format("User with username %s not found", username)));

        if (bookRepository.getById(bookId).getRequestStatus() == ACCEPTED) {
            user.getBasket().getBooks().add(bookRepository.getById(bookId));
            bookRepository.incrementBasketsOfBooks(bookId);
        } else {
            throw new BadRequestException("This book has not went through admin-check yet!");
        }
        log.info("Edd book to basket works");
        return ResponseEntity.ok(new MessageResponse(String.format("Book with id %s has been added to basket of user" +
                "with username %s", bookId, username)));
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(ClientUpdateDTO newClientDTO, String username) {
        User user = userRepository.getUser(username).orElseThrow(() ->
                new BadRequestException(String.format("User with username %s has not been found", username)));

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
        log.info("Update Client works");
        return ResponseEntity.ok("User have changed details");
    }

    @Override
    public ClientRegisterDTO getClientDetails(String username) {
        return clientRegisterMapper.createDTO(userRepository.getUser(username).orElseThrow(() ->
                new BadRequestException(String.format("User with username %s has not been found!", username))));
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteBookFromBasket(Long id, String email) {
        User user = userRepository.getUser(email).orElseThrow(() ->
                new BadRequestException(String.format("User with id %s has not been found!", email)));
        Book book = bookRepository.getById(id);
        if(book.getBookType().equals(BookType.PAPERBOOK)){
            book.getPaperBook().setNumberOfSelected(book.getPaperBook().getNumberOfSelectedCopy());
            bookRepository.save(book);
        }
        user.getBasket().getBooks().remove(book);
        log.info("delete book from basket works");
        return ResponseEntity.ok("Delete book from basket of " + email);
    }

    @Override
    @Transactional
    public ResponseEntity<?> cleanBasketOfClientByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(
                        "Client with email = " + email + " does not exists"
                ));
        List<Book> basketByClientId = bookRepository.findBasketByClientId(email);
        for (Book book : basketByClientId) {
            if (book.getBookType().equals(BookType.PAPERBOOK)){
                book.getPaperBook().setNumberOfSelected(book.getPaperBook().getNumberOfSelectedCopy());
                bookRepository.save(book);
            }
        }
        user.getBasket().clear();
        log.info("Clean Basket of Client by email works");
        return ResponseEntity.ok("Clean books from basket of " + email);
    }

    @Override
    public ClientOperationDTO sumAfterPromo(String promo, String name) {
        List<Book> books = bookRepository.findBasketByClientId(name);
        Double sum = 0.0;
        for (Book book : books) {
            if (book.getDiscountFromPromo() != null) {
                if (checkPromo(promo)) {
                    sum += (book.getPrice() * book.getDiscountFromPromo()) / 100;
                }else
                    log.info("Your promo code is not suitable");
            }
            continue;
        }
        ClientOperationDTO clientOperationDTO = clientOperationMapper.create(name);

        Double total = clientOperationDTO.getTotal() - sum;
        Double discountPromo = clientOperationDTO.getDiscount() + sum;

        clientOperationDTO.setTotal(total);
        clientOperationDTO.setDiscount(discountPromo);

        return clientOperationDTO;
    }

    @Override
    public List<BookResponse> getBooksFromBasket(String clientId) {
        return bookRepository.findBasketByClientId(clientId)
                .stream().map(book -> modelMapper.map(
                        book, BookResponse.class)).collect(Collectors.toList());
    }

//    @Override
//    public ClientOperationDTO getBooksInBasket(String id) {
//        return clientOperationMapper.create(id);
//    }

    @Override
    public ResponseEntity<?> placeOrder(String name) {

        ClientOperations clientOperations = new ClientOperations();

        List<Book> all = bookRepository.findBasketByClientId(name);

        User user = userRepository.getUser(name)
                .orElseThrow(() -> new BadRequestException(
                        "user with email ={} does not exists "
                ));
        clientOperations.setBoughtBooks(all);
        clientOperations.setUser(user);

        for (Book book : all) {
            book.setOperations(clientOperations);
            if(book.getBookType().equals(BookType.PAPERBOOK)){
                book.getPaperBook().setNumberOfSelectedCopy(book.getPaperBook().getNumberOfSelected());
                bookRepository.save(book);
            }
        }
        clientOperationRepository.save(clientOperations);
        user.getBasket().clear();
        userRepository.save(user);
        return ResponseEntity.ok("Your order has been successfully placed!");
    }


    @Override
    public List<BookResponse> getBooksInPurchased(String name) {
        return userRepository.getBooksInPurchased(name);
    }


    public Long getUsersBasketId(String username) {
        return basketRepository.getUsersBasketId(username);
    }

    public Boolean checkPromo(String promo) {
        List<Promocode> promocode = repo.findAll();
        for (Promocode promocode1 : promocode) {
            if (promocode1.getPromocode().equals(promo)) {
                if (promocode1.getIsActive().equals(true)) {
                    return true;
                }
                log.error("this {} promo not found", promo);
            }
            log.error("this {} promo is not active", promocode);
        }
        return false;
    }
//    @Override
//    public List<CardResponse> getAllInCard(String name) {
//        List<CardResponse> cardResponses = bookRepository.findBasketByClientId(name)
//                .stream().map(book -> modelMapper.map(
//                        book, CardResponse.class))
//                .map(BookResponse -> modelMapper.map(BookResponse,
//                        CardResponse.class)).collect(Collectors.toList());
//        return cardOperationResponse.create(name,cardResponses,"",null);
//
//    }

    @Override
    public List<CardResponse> plusOrMinus(String name, String plusOrMinus, Long bookId) {
        List<CardResponse> cardResponses = bookRepository.findBasketByClientId(name)
                .stream().map(book -> modelMapper.map(
                        book, CardResponse.class))
                .map(BookResponse -> modelMapper.map(BookResponse,
                        CardResponse.class)).collect(Collectors.toList());
        return cardOperationResponse.create(name, cardResponses, plusOrMinus, bookId);
    }
}
