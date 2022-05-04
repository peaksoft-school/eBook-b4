package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.enums.Language;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.db.models.notEntities.SortBooksGlobal;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.db.service.BookGetService;
import kg.peaksoft.ebookb4.db.service.PromoService;
import kg.peaksoft.ebookb4.dto.dto.others.CustomPageRequest;
import kg.peaksoft.ebookb4.dto.request.GenreRequest;
import kg.peaksoft.ebookb4.dto.response.BookResponse;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static kg.peaksoft.ebookb4.db.models.enums.Genre.*;
import static kg.peaksoft.ebookb4.db.models.enums.Genre.JOURNAL;
import static kg.peaksoft.ebookb4.db.models.enums.RequestStatus.*;

@Slf4j
@Service
@AllArgsConstructor
public class BookGetServiceImpl implements BookGetService {

    private final BookRepository repository;
    private PromoService promoService;

    @Override
    public List<Book> findByGenre(Genre genre, RequestStatus requestStatus) {
        promoService.checkPromos();
        log.info("Find By Genre works");
        return repository.findAllByGenre(genre, requestStatus);
    }

    @Override
    public List<Book> findBooksByName(String name, RequestStatus requestStatus) {
        log.info("Find boos by name works");
        return repository.findByName(name, requestStatus);
    }

    @Override
    public List<Book> getAllBooksOrSortedOnes(SortBooksGlobal sortBook, int offset, int pageSize) {
        promoService.checkPromos();
        List<Book> books = repository.findAllActive(ACCEPTED);
        //if it is empty it returns empty list
        if(books.size()<1){
            log.info("if it is empty it returns empty list");
            return books;
        }
        //sorting if there are selected genres
        if (sortBook.getGenre() != null) {
            log.info("I am in sort by genre");
            if (sortBook.getGenre().size() > 1) {
                int counter = 0;
                for(Iterator<Book> iterator = books.iterator(); iterator.hasNext();) {
                    Book book = iterator.next();
                    for (Genre g: sortBook.getGenre()){
                        if(book.getGenre().equals(g)){
                            counter++;
                        }
                    }
                    if(counter==0){
                        iterator.remove();
                    }
                    counter=0;
                }
            } else {
                log.info("In else of sort by genre");
                books.removeIf(book -> book.getGenre().equals(sortBook.getGenre().get(0)));
            }
        }

        //sorting if selected min price and max price
        if (sortBook.getMin() != null && sortBook.getMax() != null) {
            log.info("I am in sort by price");
            books.removeIf(i -> i.getPrice() < sortBook.getMin() || i.getPrice() > sortBook.getMax());
        }

        //sorting if there are selected bookType
        if (sortBook.getBookType() != null) {
            log.info("I am sort by book type");
            books.removeIf(i -> !i.getBookType().equals(sortBook.getBookType()));
        }
        System.out.println(books.size());
        //sorting if there are selected language
        if (sortBook.getLanguage() != null) {
            log.info("I am sort by language");
            if (sortBook.getLanguage().size() > 1) {
                int counter = 0;
                for(Iterator<Book> iterator = books.iterator(); iterator.hasNext();) {
                    Book book = iterator.next();
                    for (Language l: sortBook.getLanguage()){
                        if(book.getLanguage().equals(l)){
                            counter++;
                        }
                    }
                    if(counter==0){
                        iterator.remove();
                    }
                    counter=0;
                }
            } else {
                log.info("In else of sort by language");
                books.removeIf(book -> !book.getLanguage().equals(sortBook.getLanguage().get(0)));
            }
        }

        Pageable paging = PageRequest.of(offset, pageSize);
        int start = Math.min((int)paging.getOffset(), books.size());
        int end = Math.min((start + paging.getPageSize()), books.size());
        Page<Book> pages = new PageImpl<>(books.subList(start, end), paging, books.size());
        log.info("Sort book works");
        return new CustomPageRequest<Book>(pages).getContent();
    }

    @Override
    public Book getBookById(Long id) {
        promoService.checkPromos();
        log.info("Get book by id works");
        return repository.findBookByIdAndActive(id, ACCEPTED).orElseThrow(()->
                new BadRequestException("This book is not went through admin-check yet!"));
    }

    @Override
    public List<BookResponse> getAllBooksRequests() {
        log.info("Get all books request works");
        return repository.findBooksInProgress(INPROGRESS);
    }

    @Override
    public List<BookResponse> getAllAcceptedBooks() {
        log.info("accepted books size =s%"+repository.findBooksAccepted(ACCEPTED).size());
        return repository.findBooksAccepted(ACCEPTED);
    }

    @Override
    public List<GenreRequest> getCountGenre() {

        List<GenreRequest> genreRequest = new ArrayList<>();
        genreRequest.add(new GenreRequest(LITERATURE));
        genreRequest.add(new GenreRequest(ROMANCE));
        genreRequest.add(new GenreRequest(FANTASY));
        genreRequest.add(new GenreRequest(DETECTIVE));
        genreRequest.add(new GenreRequest(SCIENTIFIC));
        genreRequest.add(new GenreRequest(ADVENTURE));
        genreRequest.add(new GenreRequest(INTERNATIONAL_LITERATURE));
        genreRequest.add(new GenreRequest(BIOGRAPHY));
        genreRequest.add(new GenreRequest(DETECTIVE));
        genreRequest.add(new GenreRequest(POETRY));
        genreRequest.add(new GenreRequest(HORROR));
        genreRequest.add(new GenreRequest(JOURNAL));

        for (GenreRequest request: genreRequest) {
            request.setCount(repository.getCountGenre(request.getGenre(),ACCEPTED));
        }
        genreRequest.forEach(System.out::println);
        log.info("Get count Genre works");
        return genreRequest;
    }

    @Override
    public List<Book> booksIsBestseller() {
        return repository.findAllByIsBestSeller();
    }

    @Override
    public List<Book> BooksNovelties(LocalDate localDate) {

        List<Book> books = repository.booksNovelties(localDate);

        for (Book book: books) {
            if (book.getDateOfRegister().getDayOfMonth() < localDate.getDayOfMonth()) {
                return books;
            }
        }
        return null;
    }
}