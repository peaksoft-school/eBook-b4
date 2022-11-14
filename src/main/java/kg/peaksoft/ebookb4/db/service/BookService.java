package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.db.models.entity.Genre;
import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.dto.BookDTO;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.dto.response.BookResponse;
import kg.peaksoft.ebookb4.dto.response.BookResponseAfterSaved;
import kg.peaksoft.ebookb4.dto.response.CountForAdmin;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {

    BookResponseAfterSaved saveBook(BookDTO bookDTO, String username);

    Book findByBookId(Long bookId);

    ResponseEntity<?> delete(Long bookId);

    ResponseEntity<?> update(BookDTO bookDTO, Long userId, Long genreId);

    List<Book> findBooksFromVendor(Integer offset, int pageSize, String username);

    List<Book> findBooksFromVendorInFavorites(Integer offset, int pageSize, String username);

    List<Book> findBooksFromVendorAddedToBasket(Integer offset, int pageSize, String username);

    List<Book> findBooksFromVendorWithDiscount(Integer offset, int pageSize, String username);

    List<Book> findBooksFromVendorCancelled(Integer offset, int pageSize, String username, RequestStatus status);

    List<Book> findBooksFromVendorInProcess(Integer offset, int pageSize, String username, RequestStatus status);

    List<BookResponse> getBooksSold(String name, ERole role);

    List<Genre> getAllGenres();

    CountForAdmin getCountOfVendorBooks(String name);

}
