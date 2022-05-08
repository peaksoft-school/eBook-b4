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

            if (all.get(i).getBookType().equals(BookType.PAPERBOOK) && all.get(i).getPaperBook().getNumberOfSelected() > 0) {
                if (plusOrMinus.equals("plus")) {
                    if (all.get(i).getPaperBook().getNumberOfSelected() > 1) {
                        log.info("qq.get(i).getCountOfPaperBook()= {}", qq.get(i).getCountOfPaperBook());
                        Integer numberOfSelectedCopy = all.get(i).getPaperBook().getNumberOfSelectedCopy();
                        System.out.println(numberOfSelectedCopy);
                        Integer count = count(all);
                        int minus = minus(all);
                        int i1 = numberOfSelectedCopy - minus;
                        qq.get(i).setCountOfPaperBook(i1);
                        qq.get(i).setCountOfBooksInTotal(count + (i1 -countOfPaperBook(all)));
                        qq.get(i).setDiscount(discount(all) + (discountForPaperBook(all) * (i1-countOfPaperBook(all))));
                        qq.get(i).setSum(priseSum(all) + (priseSumForPaperBook(all) * i1));
                        qq.get(i).setTotal(total(all) + (totalForPaperBook(all) * i1));
                    } else {
                        log.info("закончились бумажные книги");
                        continue;
                    }
                } else if (plusOrMinus.equals("minus")) {
                    Integer numberOfSelectedCopy = all.get(i).getPaperBook().getNumberOfSelectedCopy();
                    System.out.println(numberOfSelectedCopy);
                    Integer count = count(all);
                    int plus = plus(all);
                    int abs = Math.abs(plus - numberOfSelectedCopy);
                    qq.get(i).setCountOfPaperBook(Math.abs(plus - numberOfSelectedCopy));
                    qq.get(i).setCountOfBooksInTotal(count + (abs -countOfPaperBook(all)));
                    qq.get(i).setDiscount(discount(all) + (discountForPaperBook(all) * (abs-countOfPaperBook(all))));
                    qq.get(i).setSum(priseSum(all) + (priseSumForPaperBook(all) * abs));
                    qq.get(i).setTotal(total(all) + (totalForPaperBook(all) * abs));
                }
            } else {
                log.error("закончились бумажные книги");
                log.info("Else if working ");
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

    public int countOfPaperBook(List<Book> list){
        Integer sum = 0;
        for (Book book : list) {
            if (book.getBookType().equals(BookType.PAPERBOOK)){
                sum++;
            }
        }
        return sum;
    }

    public Double discountForPaperBook(List<Book> list) {
        double sumAfterDiscount = 0;
        for (Book book : list) {
            if (book.getBookType().equals(BookType.PAPERBOOK)) {
                if (book.getDiscount() != null) {
                    sumAfterDiscount += (book.getPrice() * book.getDiscount()) / 100;
                } else {
                    continue;
                }
            }
        }
        return sumAfterDiscount;
    }

    public Double total(List<Book> list) {
        Double sumAfterDiscount = 0.0;
        Double sum = 0.0;
        for (Book book : list) {
            if (book.getBookType().equals(BookType.EBOOK) || book.getBookType().equals(BookType.AUDIOBOOK)) {
                if (book.getDiscount() != null) {
                    sumAfterDiscount += (book.getPrice() * book.getDiscount()) / 100;
                }
                continue;
            }
        }
        for (Book book : list) {
            if (book.getBookType().equals(BookType.EBOOK) || book.getBookType().equals(BookType.AUDIOBOOK)) {
                sum += book.getPrice();
            }
        }
        return sum - sumAfterDiscount;
    }

    public Double totalForPaperBook(List<Book> list) {
        Double sumAfterDiscount = 0.0;
        Double sum = 0.0;
        for (Book book : list) {
            if (book.getBookType().equals(BookType.PAPERBOOK)) {
                if (book.getDiscount() != null) {
                    sumAfterDiscount += (book.getPrice() * book.getDiscount()) / 100;
                }
                continue;
            }
        }
        for (Book book : list) {
            if (book.getBookType().equals(BookType.PAPERBOOK)) {
                sum += book.getPrice();
            }
        }
        return sum - sumAfterDiscount;
    }

    public Double priseSum(List<Book> list) {
        Double sum = 0.0;
        for (Book book : list) {
            if (book.getBookType().equals(BookType.EBOOK) || book.getBookType().equals(BookType.AUDIOBOOK)){
                sum += book.getPrice();
            }
        }
        return sum;
    }



    public Double priseSumForPaperBook(List<Book> list){
        Double sum = 0.0;
        for (Book book : list) {
            if (book.getBookType().equals(BookType.PAPERBOOK)) {
                sum += book.getPrice();
            }
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

    public int minus(List<Book> list){
        int numForReturn = 0;
        for (Book book: list) {
            if (book.getBookType().equals(BookType.PAPERBOOK)) {
                Book book1 = bookRepository.getById(book.getBookId());
                int numberOfSelected = book1.getPaperBook().getNumberOfSelected();
                numForReturn = numberOfSelected - 1;
                book1.getPaperBook().setNumberOfSelected(numberOfSelected - 1);
                bookRepository.save(book1);
                return (numberOfSelected - 1);
            }
        }
        return numForReturn;
    }

    public int plus(List<Book> list){
        int numForReturn = 0;
        for (Book book : list) {
            if (book.getBookType().equals(BookType.PAPERBOOK)) {
                Book book1 = bookRepository.getById(book.getBookId());
                int numberOfSelected = book1.getPaperBook().getNumberOfSelected();
                numForReturn = numberOfSelected + 1;
                book1.getPaperBook().setNumberOfSelected(numberOfSelected + 1);
                bookRepository.save(book1);
                return (numberOfSelected + 1);
            }
        }
        return numForReturn;
    }
}
