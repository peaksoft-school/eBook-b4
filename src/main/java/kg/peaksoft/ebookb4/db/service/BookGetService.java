package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.db.models.notEntities.SortBooksGlobal;
import kg.peaksoft.ebookb4.dto.request.GenreRequest;
import kg.peaksoft.ebookb4.dto.response.BookResponse;

import java.util.List;

public interface BookGetService {


    List<Book> findByGenre(Genre genre, RequestStatus requestStatus);

    List<Book> findBooksByName(String name, RequestStatus requestStatus);

    List<Book> getAllBooksOrSortedOnes(SortBooksGlobal sortBook, int offset, int pageSize);

    Book getBookById(Long id);

    List<BookResponse> getAllBooksRequests();

    List<BookResponse> getAllAcceptedBooks();

    List<GenreRequest> getCountGenre();

}
