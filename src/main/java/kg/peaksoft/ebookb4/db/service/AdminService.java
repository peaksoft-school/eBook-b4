package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.dto.response.BookResponse;
import kg.peaksoft.ebookb4.dto.response.ClientResponse;
import kg.peaksoft.ebookb4.dto.response.CountForAdmin;
import kg.peaksoft.ebookb4.dto.response.VendorResponse;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.dto.request.RefuseBookRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {

    List<BookResponse> getBooksFromBasket(Long id);

    List<VendorResponse> findAllVendors();

    ResponseEntity<?> deleteById(String email);

    ResponseEntity<?> deleteByIdAdmin(Long userId);

    ResponseEntity<?> deleteBookById(Long id);

    List<ClientResponse> findAllClient();

    VendorResponse getVendorById(Long id);

    ClientResponse getClientById(Long id);

    ResponseEntity<?> acceptBookRequest(Long bookId);

    ResponseEntity<?> refuseBookRequest(RefuseBookRequest request, Long id);

    Book getBookById(Long bookId);

    List<Book> findBooksFromVendor(Integer offset, int pageSize, Long vendorId);

    List<Book> findBooksFromVendorInFavorites(Integer offset, int pageSize, Long vendorId);

    List<Book> findBooksFromVendorAddedToBasket(Integer offset, int pageSize, Long vendorId);

    List<Book> findBooksFromVendorWithDiscount(Integer offset, int pageSize, Long vendorId);

    List<Book> findBooksFromVendorCancelled(Integer offset, int pageSize, Long vendorId, RequestStatus status);

    List<Book> findBooksFromVendorInProcess(Integer offset, int pageSize, Long vendorId, RequestStatus status);

    List<Book> getBooksInPurchased(Long clientId);

    List<BookResponse> getAllLikedBooks(Long clientId);

    CountForAdmin getCountOfInProgressAlsoDontWatched();

    Integer getCountOfDidNotWatched(List<Book> bookList);

}
