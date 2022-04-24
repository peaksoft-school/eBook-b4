package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.dto.response.ClientResponse;
import kg.peaksoft.ebookb4.dto.response.VendorResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {

    List<Book> getBooks();

    List<Book> getBooksBy(Genre genre, BookType bookType);

    List<Book> getBooksByGenre(Genre genre);

    List<Book> getBooksByBookType(BookType bookType);

    List<VendorResponse> findAllVendors();

    ResponseEntity<?> deleteById(Long id);

    List<ClientResponse> findAllClient();

    VendorResponse getVendor(Long id);

}
