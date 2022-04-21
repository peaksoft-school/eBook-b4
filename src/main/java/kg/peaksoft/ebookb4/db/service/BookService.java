package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.dto.request.BookRequest;
import kg.peaksoft.ebookb4.db.models.books.Book;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    ResponseEntity<?> register(BookRequest bookRequest, String username);

    Book findByBookId(Long bookId);

    ResponseEntity<?> delete(Long bookId);

    ResponseEntity<?> update(BookRequest bookRequest, Long userId);

    List<Book> findBooksFromVendor(Integer integer, int i, String username);

    List<Book> findBooksFromVendorInFavorites(Integer integer, int i, String username);

    List<Book> findBooksFromVendorInBasket(Integer integer, int i, String username);

    List<Book> findBooksFromVendorWithDiscount(Integer integer, int i, String username);

    List<Book> findBooksFromVendorInProcess(Integer integer, int i, String username);

    List<Book> findBooksFromVendorInCancelled(Integer integer, int i, String username);



}
