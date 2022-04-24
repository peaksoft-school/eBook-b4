package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.dto.response.VendorResponse;

import java.util.List;

public interface AdminService {

    List<Book> getBooks();

    List<Book> getBooksBy(Genre genre, BookType bookType);

    List<Book> getBooksByGenre(Genre genre);

    List<Book> getBooksByBookType(BookType bookType);

    List<VendorResponse> findAllVendors();

    void deleteVendor(Long id);

}
