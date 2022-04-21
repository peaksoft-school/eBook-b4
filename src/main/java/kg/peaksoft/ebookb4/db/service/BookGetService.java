package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.booksClasses.SortBook;

import java.util.List;

public interface BookGetService {


    List<Book> findByGenre(Genre genre);

    List<Book> findBooksByName(String name);

    List<Book> getAllBooksOrSortedOnes(SortBook sortBook, int offset, int pageSize);

    Book getBookById(Long id);


}
