package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.bookClasses.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.enums.Language;
import kg.peaksoft.ebookb4.db.models.others.SortBook;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.db.service.BookGetService;
import kg.peaksoft.ebookb4.db.service.PromoService;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public List<Book> getAllBooksOrSortedOnes(SortBook sortBook) {
        promoService.checkPromos();
        int counter = 0;
        List<Book> books = repository.findAllActive();
        System.out.println(books);
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
                for(Iterator<Book> iterator = books.iterator(); iterator.hasNext();) {
                    for (Genre j: sortBook.getGenre()){
                        if(iterator.next().getGenre().equals(j)){
                            counter++;
                        }
                    }
                    if(counter==0){
                        iterator.remove();
                    }
                }
                counter = 0;
            } else {
                books.removeIf(book -> book.getGenre().equals(sortBook.getGenre().get(0)));
            }
        }

        //sorting if selected min price and max price
        if (sortBook.getMin() != null && sortBook.getMax() != null) {
            System.out.println("I am sort by price");
            for(Iterator<Book> iterator = books.iterator(); iterator.hasNext();){
                if(iterator.next().getPrice()<sortBook.getMin()||
                iterator.next().getPrice()>sortBook.getMax()){
                    iterator.remove();
                }
            }
        }

        //sorting if there are selected bookType
        if (sortBook.getBookType() != null) {
            System.out.println("I am sort by BookType");
            books.removeIf(book -> book.getBookType() != sortBook.getBookType());
        }

        //sorting if there are selected language
        if (sortBook.getLanguage() != null) {
            System.out.println("I am in sort by language!");
            if (sortBook.getLanguage().size() > 1) {
                for(Iterator<Book> iterator = books.iterator(); iterator.hasNext();){
                    for(Language l : sortBook.getLanguage()){
                        if(iterator.next().getLanguage().equals(l)){
                            counter++;
                        }
                    }
                    if(counter==0){
                        iterator.remove();
                    }
                }
                counter = 0;
            }
            //following code could be deleted
//            else {
//                for(Iterator<Book> iterator = books.iterator(); iterator.hasNext(); iterator.next()){
//                    if(iterator.next().getLanguage().equals(sortBook.getLanguage().get(0))){
//                        iterator.remove();
//                    }
//                }
//            }
        }

        return books;
    }

    @Override
    public Book getBookById(Long id) {
        promoService.checkPromos();
        return repository.findBookById(id).orElseThrow(()->
                new BadRequestException("This book is not went through admin-check yet!"));
    }

}
