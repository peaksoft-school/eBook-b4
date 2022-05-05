package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.db.models.notEntities.SortBooksGlobal;
import kg.peaksoft.ebookb4.db.models.entity.dto.request.GenreRequest;
import kg.peaksoft.ebookb4.db.models.entity.dto.response.BookResponse;

import java.util.List;

public interface BookGetService {


    List<Book> findByGenre(String genreName, RequestStatus requestStatus);


    List<Book> findBooksByName(String name, RequestStatus requestStatus);

    List<Book> getAllBooksOrSortedOnes(SortBooksGlobal sortBook, int offset, int pageSize);

    Book getBookById(Long id);

    List<BookResponse> getAllBooksRequests();

    List<BookResponse> getAllAcceptedBooks();

    List<GenreRequest> getCountGenre();

}
