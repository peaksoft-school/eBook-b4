package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.bookClasses.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.enums.Language;
import kg.peaksoft.ebookb4.db.models.others.SortBook;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.db.service.BookGetService;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class BookGetServiceImpl implements BookGetService {

    private final BookRepository repository;

    @Override
    public List<Book> findByGenre(Genre genre) {
        return repository.findAllByGenre(genre);
    }

    @Override
    public List<Book> findBooksByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Book> getAllBooksOrSortedOnes(SortBook sortBook) {
        int counter = 0;
        List<Book> books = repository.findAllActive();
        //if it is empty it returns empty list
        if(books.size()<1){
            return books;
        }
        //show only active ones
        for(int i = 0; i<books.size();i++){
            if(!books.get(i).getIsActive()){
                books.remove(i);
            }
        }
        //sorting if there are selected genres
        if (sortBook.getGenre() != null) {
            System.out.println("I am in sort by genre!");
            if (sortBook.getGenre().size() > 1) {
                for (int k = 0; k < books.size(); k++) {
                    for (Genre j : sortBook.getGenre()) {
                        if (books.get(k).getGenre().equals(j)) {
                            counter++;
                        }
                    }
                    if (counter == 0) {
                        books.remove(k);
                    }
                }
                counter = 0;
            } else {
                for (int j = 0; j < books.size(); j++) {
                    if (!books.get(j).getGenre().equals(sortBook.getGenre().get(0))) {
                        books.remove(j);
                    }
                }
            }
        }
        //sorting if selected min price and max price
        if (sortBook.getMin() != null && sortBook.getMax() != null) {
            System.out.println("I am sort by price");
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getPrice() < sortBook.getMin() || books.get(i).getPrice() > sortBook.getMax()) {
                    books.remove(i);
                }
            }
        }
        //sorting if there are selected bookType
        if (sortBook.getBookType() != null) {
            System.out.println("I am sort by BookType");
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getBookType() != sortBook.getBookType()) {
                    books.remove(i);
                }
            }
        }
        //sorting if there are selected language
        if (sortBook.getLanguage() != null) {
            System.out.println("I am in sort by language!");
            if (sortBook.getLanguage().size() > 1) {
                for (int k = 0; k < books.size(); k++) {
                    for (Language l : sortBook.getLanguage()) {
                        if (books.get(k).getLanguage().equals(l)) {
                            counter++;
                        }
                    }
                    if (counter == 0) {
                        books.remove(k);
                    }
                }
                counter = 0;
            } else {
                for (int j = 0; j < books.size(); j++) {
                    if (!books.get(j).getLanguage().equals(sortBook.getLanguage().get(0))) {
                        books.remove(j);
                    }
                }
            }
        }
        return books;
    }

    @Override
    public Book getBookById(Long id) {
        return repository.findBookById(id).orElseThrow(()->
                new BadRequestException("This book is not went through admin-check yet!"));
    }

}
