package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.entity.Genre;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import kg.peaksoft.ebookb4.db.service.AdminService;
import kg.peaksoft.ebookb4.dto.dto.others.CustomPageRequest;
import kg.peaksoft.ebookb4.dto.mapper.ClientMapper;
import kg.peaksoft.ebookb4.dto.mapper.VendorMapper;
import kg.peaksoft.ebookb4.dto.request.RefuseBookRequest;
import kg.peaksoft.ebookb4.dto.response.BookResponse;
import kg.peaksoft.ebookb4.dto.response.ClientResponse;
import kg.peaksoft.ebookb4.dto.response.VendorResponse;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static kg.peaksoft.ebookb4.db.models.enums.RequestStatus.ACCEPTED;
import static kg.peaksoft.ebookb4.db.models.enums.RequestStatus.REFUSED;

@Slf4j
@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private VendorMapper vendorMapper;
    private ClientMapper clientMapper;
    private ModelMapper modelMapper;
    @Override
    public List<Book> getBooksBy(String genre, BookType bookType) {
        log.info("getBooks By genre and book type works");
        return bookRepository.getBooks(genre, bookType, ACCEPTED);
    }

    @Override
    public List<BookResponse> getBooksFromBasket(Long clientId) {
        return bookRepository.findBasketByClientId(clientId)
                .stream().map(book -> modelMapper.map(
                        book, BookResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<Book> getBooksByBookType(BookType bookType) {
        log.info("get books by book type works");
        return bookRepository.findAllByBookType(bookType, ACCEPTED);
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        log.info("get books by genre works");
        return bookRepository.findAllByGenre(genre, ACCEPTED);
    }

    @Override
    public List<VendorResponse> findAllVendors() {
        List<User> users = userRepository.findAllVendors(ERole.ROLE_VENDOR);
        List<VendorResponse> vendorResponses = new ArrayList<>();
        log.info("Get all vendors works");
        for (User i : users) {
            vendorResponses.add(vendorMapper.createVendorDto(i));
        }
        return vendorResponses;
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            log.error("With id = " + id + " does not exists");
            throw new BadRequestException(
                    "Vendor with id " + id + " does not exists");
        }
        log.info("Successfully deleter");
        userRepository.deleteById(id);
        return ResponseEntity.ok("Successfully deleter");
    }

    @Override
    public ResponseEntity<?> deleteBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            log.error("Delete book by id works");
            throw new BadRequestException(
                    "Book with id " + id + " does not exists");
        }
        bookRepository.deleteById(id);
        return ResponseEntity.ok("Successfully deleter");
    }

    @Override
    public List<ClientResponse> findAllClient() {
        List<User> users = userRepository.findAllClients(ERole.ROLE_CLIENT);
        List<ClientResponse> clientResponses = new ArrayList<>();
        for (User i : users) {
            clientResponses.add(clientMapper.createClientDto(i));
        }
        log.info("Find all Clients works");
        return clientResponses;
    }

    @Override
    public VendorResponse getVendor(Long id) {
        User user = userRepository.getById(id);
        log.info("Get all vendors works");
        return vendorMapper.createVendorDto(user);
    }

    @Override
    public ClientResponse getClientById(Long id) {
        User user = userRepository.getById((id));
        log.info("Get client by id works");
        return clientMapper.createClientDto(user);
    }

    @Override
    @Transactional
    public ResponseEntity<?> acceptBookRequest(Long bookId) {
        Book book = bookRepository.findBookInProgress(bookId, RequestStatus.INPROGRESS)
                .orElseThrow(() -> {
                    log.error("Book with id ={} does not exists", bookId);
                    throw new BadRequestException(
                            String.format("Book with id %s does not exists or it is already went through admin-check", bookId)
                    );
                });
        log.info("admin accept books ");
        book.setRequestStatus(ACCEPTED);
        return ResponseEntity.status(HttpStatus.OK).body("Accepted successfully book with id = " + book.getTitle());
    }

    @Override
    @Transactional
    public ResponseEntity<?> refuseBookRequest(RefuseBookRequest refuseBookRequest, Long id) {
        Book book = bookRepository.findBookInProgress(id, RequestStatus.INPROGRESS)
                .orElseThrow(() -> {
                    log.error("Book with id ={} does not exists", id);
                    throw new BadRequestException(
                            String.format("Book with id %s does not exists or it is already went through admin-check", id)
                    );
                });
        book.setRequestStatus(REFUSED);
        // TODO: 24.04.2022  sand massage to gmail
        log.info("admin refuse book request");
        return ResponseEntity.ok().body(
                refuseBookRequest.getReason());
    }

    @Override
    @Transactional
    public ResponseEntity<?> getBookById(Long bookId) {
        Book book = bookRepository.findBookInProgress(bookId, RequestStatus.INPROGRESS)
                .orElseThrow(() -> {
                    log.error("Book with id ={} does not exists", bookId);
                    throw new BadRequestException(
                            String.format("Book with id %s has not been found it is already went through admin-check", bookId)
                    );
                });
        book.setAdminWatch(true);
        log.info("Get book by id works");
        return ResponseEntity.ok(String.format("Book with id %s has been watched by admin!", bookId));
    }

    @Override
    public List<Book> findBooksFromVendor(Integer offset, int pageSize, Long vendorId) {
        User user = userRepository.findById(vendorId)
           .orElseThrow(() -> {
            log.error("Vendor with id ={} does not exists", vendorId);
            throw new BadRequestException(
                    String.format("Vendor with id %s doesn't exist!", vendorId)
            );
        });
        List<Book> books = bookRepository.findBooksFromVendor(user.getEmail());
        Pageable paging = PageRequest.of(offset, pageSize);
        int start = Math.min((int) paging.getOffset(), books.size());
        int end = Math.min((start + paging.getPageSize()), books.size());
        Page<Book> pages = new PageImpl<>(books.subList(start, end), paging, books.size());
        System.out.println(new CustomPageRequest<>(pages).getContent().size());
        log.info("Vendor books=s%"+new CustomPageRequest<>(pages).getContent().size());
        return new CustomPageRequest<>(pages).getContent();
    }

    @Override
    public List<Book> findBooksFromVendorInFavorites(Integer offset, int pageSize, Long vendorId) {
        User user = userRepository.findById(vendorId)
                .orElseThrow(() -> {
                    log.error("Vendor with id ={} does not exists", vendorId);
                    throw new BadRequestException(
                            String.format( "Vendor with id %s doesn't exist!", vendorId)
                    );
                });
        List<Book> likedBooksFromVendor = bookRepository.findLikedBooksFromVendor(user.getEmail());
        Pageable paging = PageRequest.of(offset, pageSize);
        int start = Math.min((int) paging.getOffset(), likedBooksFromVendor.size());
        int end = Math.min((start + paging.getPageSize()), likedBooksFromVendor.size());
        Page<Book> pages = new PageImpl<>(likedBooksFromVendor.subList(start, end), paging, likedBooksFromVendor.size());
        return new CustomPageRequest<>(pages).getContent();
    }

    @Override
    public List<Book> findBooksFromVendorAddedToBasket(Integer offset, int pageSize, Long vendorId) {
        User user = userRepository.findById(vendorId)
          .orElseThrow(() -> {
            log.error("Vendor with id ={} does not exists", vendorId);
            throw new BadRequestException(
                    String.format( "Vendor with id %s doesn't exist!", vendorId)
            );
        });
        List<Book> booksWithBasket = bookRepository.findBooksFromVendorAddedToBasket(user.getEmail());
        Pageable paging = PageRequest.of(offset, pageSize);
        int start = Math.min((int) paging.getOffset(), booksWithBasket.size());
        int end = Math.min((start + paging.getPageSize()), booksWithBasket.size());
        Page<Book> pages = new PageImpl<>(booksWithBasket.subList(start, end), paging, booksWithBasket.size());
        return new CustomPageRequest<>(pages).getContent();
    }

    @Override
    public List<Book> findBooksFromVendorWithDiscount(Integer offset, int pageSize, Long vendorId) {
        User user = userRepository.findById(vendorId).orElseThrow(() ->
                new BadRequestException(String.format("Vendor with id %s doesn't exist!", vendorId)));
        List<Book> booksWithDiscount = bookRepository.findBooksFromVendorWithDiscount(user.getEmail());
        Pageable paging = PageRequest.of(offset, pageSize);
        int start = Math.min((int) paging.getOffset(), booksWithDiscount.size());
        int end = Math.min((start + paging.getPageSize()), booksWithDiscount.size());
        Page<Book> pages = new PageImpl<>(booksWithDiscount.subList(start, end), paging, booksWithDiscount.size());
        return new CustomPageRequest<>(pages).getContent();
    }

    @Override
    public List<Book> findBooksFromVendorCancelled(Integer offset, int pageSize, Long vendorId,
                                                   RequestStatus requestStatus) {
        User user = userRepository.findById(vendorId).orElseThrow(() ->
                new BadRequestException(String.format("Vendor with id %s doesn't exist!", vendorId)));
        List<Book> booksWithCancel = bookRepository.findBooksFromVendorWithCancel(user.getEmail(), requestStatus);
        Pageable paging = PageRequest.of(offset, pageSize);
        int start = Math.min((int) paging.getOffset(), booksWithCancel.size());
        int end = Math.min((start + paging.getPageSize()), booksWithCancel.size());
        Page<Book> pages = new PageImpl<>(booksWithCancel.subList(start, end), paging, booksWithCancel.size());
        return new CustomPageRequest<>(pages).getContent();
    }

    @Override
    public List<Book> findBooksFromVendorInProcess(Integer offset, int pageSize, Long vendorId,
                                                   RequestStatus requestStatus) {
        User user = userRepository.findById(vendorId).orElseThrow(() ->
                new BadRequestException(String.format("Vendor with id %s doesn't exist!", vendorId)));
        List<Book> booksInProgress = bookRepository.findBooksFromVendorInProgress(user.getEmail(), requestStatus);
        Pageable paging = PageRequest.of(offset, pageSize);
        int start = Math.min((int) paging.getOffset(), booksInProgress.size());
        int end = Math.min((start + paging.getPageSize()), booksInProgress.size());
        Page<Book> pages = new PageImpl<>(booksInProgress.subList(start, end), paging, booksInProgress.size());
        return new CustomPageRequest<>(pages).getContent();
    }
}