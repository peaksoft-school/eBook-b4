package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.db.models.notEntities.SortBooksGlobal;
import kg.peaksoft.ebookb4.dto.request.GenreRequest;
import kg.peaksoft.ebookb4.dto.response.BookResponse;

import java.util.List;

public interface BookGetService {

    List<Book> findByGenre(String genreName, RequestStatus status);

    List<Book> findBooksByName(String name, RequestStatus status);

    List<Book> getAllBooksOrSortedOnes(SortBooksGlobal sortBook, int offset, int pageSize);

    Book getBookById(Long id);

    List<BookResponse> getAllBooksInProgress(int offset, int pageSize);

    List<Book> getAllAcceptedBooks(int offset, int pageSize, Long genreId, BookType bookType);

    List<GenreRequest> getCountGenre();

    List<Book> booksIsBestseller();

    List<Book> BooksNovelties();

    List<Book> getAllAudioBook();

    List<Book> getAllEBook();

    List<Book> getBook();

}
