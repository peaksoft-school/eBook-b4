package kg.peaksoft.ebookb4.db.models.mappers;

import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.response.CardResponse;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class CardOperationResponse {
    private BookRepository bookRepository;

    public List<CardResponse> create(String name, List<CardResponse> qq,String plusOrMinus) {

        List<Book> all = bookRepository.findBasketByClientId(name);
        for (int i = 0; i < qq.size(); i++) {
            qq.get(i).setBookId(all.get(i).getBookId());
            qq.get(i).setTitle(all.get(i).getTitle());
            qq.get(i).setAuthorFullName(all.get(i).getAuthorFullName());
            qq.get(i).setAboutBook(all.get(i).getAboutBook());
            qq.get(i).setPublishingHouse(all.get(i).getPublishingHouse());
            qq.get(i).setYearOfIssue(all.get(i).getYearOfIssue());
            qq.get(i).setPrice(all.get(i).getPrice());

            if (all.get(i).getBookType().equals(BookType.PAPERBOOK)) {
                if (all.get(i).getPaperBook().getNumberOfSelected() > 0) {
                    if (plusOrMinus.equals("minus")){
                        qq.get(i).setCountOfBooksInTotal(count(all)+1);
                        qq.get(i).setDiscount(discount(all));
                        qq.get(i).setSum(priseSum(all));
                        qq.get(i).setCountOfPaperBook(minus(all));
                    } else if (plusOrMinus.equals("plus")) {
                        qq.get(i).setCountOfBooksInTotal(count(all)-1);
                        qq.get(i).setDiscount(discount(all));
                        qq.get(i).setSum(priseSum(all));
                        qq.get(i).setCountOfPaperBook(plus(all));
                    }
                } else if (all.get(i).getPaperBook().getNumberOfSelected() <= 0) {
                    log.error("закончились бумажные книги");
                }
            }else{
                qq.get(i).setCountOfBooksInTotal(count(all));
                qq.get(i).setDiscount(discount(all));
                qq.get(i).setSum(priseSum(all));
                qq.get(i).setTotal(total(all));
            }
        }
        return qq;
    }

    public Double discount(List<Book> list) {
        double sumAfterDiscount = 0;
        for (Book book : list) {
            if (book.getDiscount() != null) {
                sumAfterDiscount += (book.getPrice() * book.getDiscount()) / 100;
            } else {
                continue;
            }
        }
        return sumAfterDiscount;
    }

    public Double total(List<Book> list) {
        Double sumAfterDiscount = 0.0;
        Double sum = 0.0;
        for (Book book : list) {
            if (book.getDiscount() != null) {
                sumAfterDiscount += (book.getPrice() * book.getDiscount()) / 100;
            }
            continue;
        }
        for (Book book : list) {
            sum += book.getPrice();
        }
        return sum - sumAfterDiscount;
    }

    public Double priseSum(List<Book> list) {
        Double sum = 0.0;
        for (Book book : list) {
            sum += book.getPrice();
        }
        return sum;
    }


    public Integer count(List<Book> list) {
        Integer sum = 0;
        for (Book book : list) {
            sum++;
        }
        return sum;
    }

    public int plus(List<Book> list){
        int numForReturn = 0;
        for (Book book: list) {
            Book book1 = bookRepository.getById(book.getBookId());
            int numberOfSelected = book1.getPaperBook().getNumberOfSelected();
            numForReturn =numberOfSelected -1;
            book1.getPaperBook().setNumberOfSelected(numberOfSelected-1);
            bookRepository.save(book1);
            return (numberOfSelected + 1);
        }
        return numForReturn;
    }

    public int minus(List<Book> list){
        int numForReturn = 0;
        for (Book book: list) {
            Book book1 = bookRepository.getById(book.getBookId());
            int numberOfSelected = book1.getPaperBook().getNumberOfSelected();
            numForReturn = numberOfSelected + 1;
            book1.getPaperBook().setNumberOfSelected(numberOfSelected+1);
            bookRepository.save(book1);
            return (numberOfSelected +1);
        }
        return numForReturn;
    }
}
