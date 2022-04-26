package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import kg.peaksoft.ebookb4.db.service.AdminService;
import kg.peaksoft.ebookb4.dto.mapper.ClientMapper;
import kg.peaksoft.ebookb4.dto.mapper.VendorMapper;
import kg.peaksoft.ebookb4.dto.request.RefuseBookRequest;
import kg.peaksoft.ebookb4.dto.response.ClientResponse;
import kg.peaksoft.ebookb4.dto.response.VendorResponse;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import kg.peaksoft.ebookb4.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static kg.peaksoft.ebookb4.db.models.enums.RequestStatus.ACCEPTED;
import static kg.peaksoft.ebookb4.db.models.enums.RequestStatus.REFUSED;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private VendorMapper vendorMapper;
    private ClientMapper clientMapper;

    @Override
    public List<Book> getBooksBy(Genre genre, BookType bookType) {
        return bookRepository.getBooks(genre, bookType, ACCEPTED);
    }

    @Override
    public List<Book> getBooksByBookType(BookType bookType) {
        return bookRepository.findAllByBookType(bookType, ACCEPTED);
    }

    @Override
    public List<VendorResponse> findAllVendors() {
        List<User> users = userRepository.findAllVendors(ERole.ROLE_VENDOR);
        List<VendorResponse> vendorResponses = new ArrayList<>();
        for (User i : users) {
            vendorResponses.add(vendorMapper.createVendorDto(i));
        }
        return vendorResponses;
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BadRequestException(
                    "Vendor with id " + id + " does not exists");
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("Successfully deleter");
    }

    @Override
    public ResponseEntity<?> deleteBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BadRequestException(
                    "Vendor with id " + id + " does not exists");
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
        return clientResponses;
    }

    @Override
    public VendorResponse getVendor(Long id) {
        User user = userRepository.getById(id);
        return vendorMapper.createVendorDto(user);
    }

    @Override
    public ClientResponse getClientById(Long id) {
        User user = userRepository.getById((id));
        return clientMapper.createClientDto(user);
    }

    @Override
    @Transactional
    public void acceptBookRequest(Long bookId) {
        Book book = bookRepository.findBookInProgress(bookId, RequestStatus.INPROGRESS)
                .orElseThrow(() -> new BadRequestException(String.format(
                        "Book with id %s does not exists or it is already went through admin-check", bookId
                )));
        book.setRequestStatus(ACCEPTED);
    }

    @Override
    @Transactional
    public void refuseBookRequest(RefuseBookRequest refuseBookRequest, Long id) {
        Book book = bookRepository.findBookInProgress(id, RequestStatus.INPROGRESS)
                .orElseThrow(() -> new BadRequestException(String.format(
                        "Book with id %s does not exists or it is already went through admin-check", id)
                ));
        book.setRequestStatus(REFUSED);

        // TODO: 24.04.2022  sand massage to gmail

    }

    @Override
    @Transactional
    public ResponseEntity<?> getBookById(Long bookId) {
        Book book = bookRepository.findBookInProgress(bookId, RequestStatus.INPROGRESS).orElseThrow(() ->
                new BadRequestException(String.format("Book with id %s has not been found it is already went through admin-check", bookId)));
        book.setAdminWatch(true);
        return ResponseEntity.ok(String.format("Book with id %s has been watched by admin!", bookId));
    }

    @Override
    public List<Book> getBooksByGenre(Genre genre) {

        return bookRepository.findAllByGenre(genre, ACCEPTED);

    }
}