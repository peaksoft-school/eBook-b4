package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.enums.Language;
import kg.peaksoft.ebookb4.db.models.notEntities.SortBooksGlobal;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.db.service.BookGetService;
import kg.peaksoft.ebookb4.db.service.PromoService;
import kg.peaksoft.ebookb4.dto.dto.others.CustomPageRequest;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@AllArgsConstructor
public class BookGetServiceImpl implements BookGetService {

    private final BookRepository repository;
    private PromoService promoService;

    @Override
    public List<Book> findByGenre(Genre genre) {
        promoService.checkPromos();
        return repository.findAllByGenre(genre);
    }

    @Override
    public List<Book> findBooksByName(String name) {
        promoService.checkPromos();
        return repository.findByName(name);
    }

    @Override
    public List<Book> getAllBooksOrSortedOnes(SortBooksGlobal sortBook, int offset, int pageSize) {
        promoService.checkPromos();
        List<Book> books = repository.findAllActive();
        //if it is empty it returns empty list
        if(books.size()<1){
            return books;
        }
        //it should be deleted
        books.removeIf(book -> !book.getIsActive());
        //sorting if there are selected genres
        if (sortBook.getGenre() != null) {
            System.out.println("I am in sort by genre!");
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
                System.out.println("In else of sort by genre");
                books.removeIf(book -> book.getGenre().equals(sortBook.getGenre().get(0)));
            }
        }

        //sorting if selected min price and max price
        if (sortBook.getMin() != null && sortBook.getMax() != null) {
            System.out.println("I am  in sort by price");
            books.removeIf(i -> i.getPrice() < sortBook.getMin() || i.getPrice() > sortBook.getMax());
        }

        //sorting if there are selected bookType
        if (sortBook.getBookType() != null) {
            System.out.println("I am sort by BookType");
            books.removeIf(i -> !i.getBookType().equals(sortBook.getBookType()));
        }
        System.out.println(books.size());
        //sorting if there are selected language
        if (sortBook.getLanguage() != null) {
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
                books.removeIf(book -> !book.getLanguage().equals(sortBook.getLanguage().get(0)));
            }
        }
        Pageable paging = PageRequest.of(offset, pageSize);
        int start = Math.min((int)paging.getOffset(), books.size());
        int end = Math.min((start + paging.getPageSize()), books.size());
        Page<Book> pages = new PageImpl<>(books.subList(start, end), paging, books.size());
        return new CustomPageRequest<Book>(pages).getContent();
    }

    @Override
    public Book getBookById(Long id) {
        promoService.checkPromos();
        return repository.findBookByIdAndActive(id).orElseThrow(()->
                new BadRequestException("This book is not went through admin-check yet!"));
    }

}
