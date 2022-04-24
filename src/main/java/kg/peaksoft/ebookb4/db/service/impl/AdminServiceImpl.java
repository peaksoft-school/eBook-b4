package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import kg.peaksoft.ebookb4.db.service.AdminService;
import kg.peaksoft.ebookb4.dto.mapper.ClientMapper;
import kg.peaksoft.ebookb4.dto.mapper.VendorMapper;
import kg.peaksoft.ebookb4.dto.response.ClientResponse;
import kg.peaksoft.ebookb4.dto.response.Response;
import kg.peaksoft.ebookb4.dto.response.VendorResponse;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private VendorMapper vendorMapper;
    private ClientMapper clientMapper;

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBooksBy(Genre genre, BookType bookType) {
        return bookRepository.getBooks(genre, bookType);
    }

    @Override
    public List<Book> getBooksByGenre(Genre genre) {
        return bookRepository.findAllByGenre(genre);
    }

    @Override
    public List<Book> getBooksByBookType(BookType bookType) {
        return bookRepository.findAllByBookType(bookType);
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
    public List<ClientResponse> findAllClient() {
        List<User> users = userRepository.findAllClients(ERole.ROLE_CLIENT);
        List<ClientResponse> clientResponses = new ArrayList<>();
        for (User i : users) {
            clientResponses.add(clientMapper.createClientDto(i));
        }
        return clientResponses;
    }

//    @Override
//    public User getVendor(Long id) {
//        User user = userRepository.getById(id);
//        vendorMapper.createVendorDto(user);
//        return user;
//    }


}
