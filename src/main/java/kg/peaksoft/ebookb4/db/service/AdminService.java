package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.db.models.request.RefuseBookRequest;
import kg.peaksoft.ebookb4.db.models.response.BookResponse;
import kg.peaksoft.ebookb4.db.models.response.ClientResponse;
import kg.peaksoft.ebookb4.db.models.response.VendorResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {

    List<Book> getBooksBy(String genre, BookType bookType);

    List<Book> getBooksByGenre(String genre);

    List<BookResponse> getBooksFromBasket(String name);

    List<Book> getBooksByBookType(BookType bookType);

    List<VendorResponse> findAllVendors();

    ResponseEntity<?> deleteById(Long id);

    ResponseEntity<?> deleteBookById(Long id);

    List<ClientResponse> findAllClient();

    VendorResponse getVendor(Long id);

    ClientResponse  getClientById(Long id);

    ResponseEntity<?> acceptBookRequest(Long bookId);

    ResponseEntity<?> refuseBookRequest(RefuseBookRequest refuseBookRequest, Long id);

    ResponseEntity<?> getBookById(Long bookId);

    List<Book> findBooksFromVendor(Integer offset, int pageSize, Long vendorId);

    List<Book> findBooksFromVendorInFavorites(Integer offset, int pageSize, Long vendorId);

    List<Book> findBooksFromVendorAddedToBasket(Integer offset, int pageSize, Long vendorId);

    List<Book> findBooksFromVendorWithDiscount(Integer offset, int pageSize, Long vendorId);

    List<Book> findBooksFromVendorCancelled(Integer offset, int pageSize, Long vendorId,
                                            RequestStatus requestStatus);

    List<Book> findBooksFromVendorInProcess(Integer offset, int pageSize, Long vendorId,
                                            RequestStatus requestStatus);

}
