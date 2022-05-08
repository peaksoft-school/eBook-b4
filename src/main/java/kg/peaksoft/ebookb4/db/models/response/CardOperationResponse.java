package kg.peaksoft.ebookb4.db.models.response;

import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 1)Написал логику в котором если указывать 0 то выйдут сумма вся только из EBOOK,AUDIOBOOK
 * 2)Если дать ID PAPERBOOK он вычислит и выдаст всю сумму дополнительно с EBOOK,AUDIOBOOK
 * 3)сегодня поговорил с фронтендом они сказали что данные сами сохранят и смогут объеденить,
 * Если они скажут по другому сделать есть идея разделить все чтоб выдавал данные без участия 2-блок
 * Это остается до разговора с фронтендом E-Book. Версия вторая готова, если захотите перейти на версию 1 уберите коментарии в методе create
 **/

@Slf4j
@Component
@AllArgsConstructor
public class CardOperationResponse {
    private BookRepository bookRepository;

    public List<CardResponse> create(String name, List<CardResponse> cardResponseList, String plusOrMinus, Long bookId) {
        List<Book> bookListFromBasketOfClient = bookRepository.findBasketByClientId(name);
        for (int i = 0; i < cardResponseList.size(); i++) {
            cardResponseList.get(i).setBookId(bookListFromBasketOfClient.get(i).getBookId());
            cardResponseList.get(i).setTitle(bookListFromBasketOfClient.get(i).getTitle());
            cardResponseList.get(i).setAuthorFullName(bookListFromBasketOfClient.get(i).getAuthorFullName());
            cardResponseList.get(i).setAboutBook(bookListFromBasketOfClient.get(i).getAboutBook());
            cardResponseList.get(i).setPublishingHouse(bookListFromBasketOfClient.get(i).getPublishingHouse());
            cardResponseList.get(i).setYearOfIssue(bookListFromBasketOfClient.get(i).getYearOfIssue());
            cardResponseList.get(i).setPrice(bookListFromBasketOfClient.get(i).getPrice());

            if (bookListFromBasketOfClient.get(i).getBookType().equals(BookType.PAPERBOOK) && bookListFromBasketOfClient.get(i).getPaperBook().getNumberOfSelected() > 0) {
                if (bookListFromBasketOfClient.get(i).getBookId().equals(bookId)) {
                    if (plusOrMinus.equals("plus")) {
                        if (bookListFromBasketOfClient.get(i).getPaperBook().getNumberOfSelected() > 1) {
                            Integer numberOfSelectedCopy = bookListFromBasketOfClient.get(i).getPaperBook().getNumberOfSelectedCopy();
                            /*Integer count = count(bookListFromBasketOfClient);*/
                            int minus = minus(bookId);
                            int i1 = numberOfSelectedCopy - minus;
                            cardResponseList.get(i).setCountOfPaperBook(i1);
                            cardResponseList.get(i).setCountOfBooksInTotal(/*count + */  (i1));
                            cardResponseList.get(i).setDiscount(/*discount(bookListFromBasketOfClient) +*/ (discountForPaperBook(bookId) * i1));
                            cardResponseList.get(i).setSum(/*priseSum(bookListFromBasketOfClient) + */  (priseSumForPaperBook(bookId) * i1));
                            cardResponseList.get(i).setTotal(/*total(bookListFromBasketOfClient) +*/ (totalForPaperBook(bookId) * i1));
                        } else {
                            log.info("the paper books under this id = {} are over", bookListFromBasketOfClient.get(i).getBookId());
                            continue;
                        }
                    } else if (plusOrMinus.equals("minus")) {
                        Integer numberOfSelectedCopy = bookListFromBasketOfClient.get(i).getPaperBook().getNumberOfSelectedCopy();
                        System.out.println(numberOfSelectedCopy);
                        /*Integer count = count(bookListFromBasketOfClient);*/
                        int plus = plus(bookId);
                        int abs = Math.abs(plus - numberOfSelectedCopy);
                        cardResponseList.get(i).setCountOfPaperBook(abs);
                        cardResponseList.get(i).setCountOfBooksInTotal(/*count +*/ (abs));
                        cardResponseList.get(i).setDiscount(/*discount(bookListFromBasketOfClient) +*/ (discountForPaperBook(bookId) * abs));
                        cardResponseList.get(i).setSum(/*priseSum(bookListFromBasketOfClient) +*/ (priseSumForPaperBook(bookId) * abs));
                        cardResponseList.get(i).setTotal(/*total(bookListFromBasketOfClient) +*/ (totalForPaperBook(bookId) * abs));
                    }
                }
            } else {
                cardResponseList.get(i).setCountOfBooksInTotal(count(bookListFromBasketOfClient));
                cardResponseList.get(i).setDiscount(discount(bookListFromBasketOfClient));
                cardResponseList.get(i).setSum(priseSum(bookListFromBasketOfClient));
                cardResponseList.get(i).setTotal(total(bookListFromBasketOfClient));
            }
        }
        return cardResponseList;
    }

    public Double discount(List<Book> list) {
        double sumAfterDiscount = 0;
        for (Book book : list) {
            if (!book.getBookType().equals(BookType.PAPERBOOK)) {
                if (book.getDiscount() != null) {
                    sumAfterDiscount += (book.getPrice() * book.getDiscount()) / 100;
                } else {
                    log.info("if Discount in book with id {} = null you can check promo code!", book.getBookId());
                    continue;
                }
            } else
                log.error("if you want to calculate the sum after the discount, the book must be e-book or audio book");
        }
        return sumAfterDiscount;
    }

    public Double discountForPaperBook(Long bookId) {
        Book bookById = bookRepository.getById(bookId);
        double sumAfterDiscount = 0;
        if (bookById.getBookType().equals(BookType.PAPERBOOK)) {
            if (bookById.getDiscount() != null) {
                sumAfterDiscount += (bookById.getPrice() * bookById.getDiscount()) / 100;
            } else
                log.info("if Discount in book with id {} = null you can check promo code!", bookById.getBookId());
        } else
            log.error("if you want to calculate the sum after the discount, the book must be paper book");
        return sumAfterDiscount;
    }

    public Integer count(List<Book> list) {
        Integer sum = 0;
        for (Book book : list) {
            if (!book.getBookType().equals(BookType.PAPERBOOK)) {
                sum++;
            } else
                log.info("method count only works with audio books or with e-books if you want to get the count of books");
        }
        return sum;
    }

    public Double total(List<Book> list) {
        Double sumAfterDiscount = 0.0;
        Double sum = 0.0;
        for (Book book : list) {
            if (book.getBookType().equals(BookType.EBOOK) || book.getBookType().equals(BookType.AUDIOBOOK)) {
                if (book.getDiscount() != null) {
                    sumAfterDiscount += (book.getPrice() * book.getDiscount()) / 100;
                } else {
                    log.info("if Discount in book with id {} = null you can check promo code!", book.getBookId());
                    continue;
                }
            } else
                log.error("The priseSum method works only with e-books and audiobooks");
        }
        for (Book book : list) {
            if (book.getBookType().equals(BookType.EBOOK) || book.getBookType().equals(BookType.AUDIOBOOK)) {
                sum += book.getPrice();
            } else
                log.error("if you want to calculate the sum after the discount, the book must be electronic or audio book");
        }
        return sum - sumAfterDiscount;
    }

    public Double totalForPaperBook(Long bookId) {
        Book bookById = bookRepository.getById(bookId);
        Double sumAfterDiscount = 0.0;
        Double sum = 0.0;
        if (bookById.getBookType().equals(BookType.PAPERBOOK)) {
            if (bookById.getDiscount() != null) {
                sumAfterDiscount += (bookById.getPrice() * bookById.getDiscount()) / 100;
            } else
                log.info("if Discount in book with id {} = null you can check promo code!", bookById.getBookId());
        } else
            log.error(" if you want to calculate the sum after the discount, the book must be paper book");
        if (bookById.getBookType().equals(BookType.PAPERBOOK)) {
            sum += bookById.getPrice();
        } else
            log.error("if you want to calculate the sum, the book must be paper book");
        return sum - sumAfterDiscount;
    }

    public Double priseSum(List<Book> list) {
        Double sum = 0.0;
        for (Book book : list) {
            if (book.getBookType().equals(BookType.EBOOK) || book.getBookType().equals(BookType.AUDIOBOOK)) {
                sum += book.getPrice();
            } else
                log.error("The priseSum method works only with e-books and audiobooks");
        }
        return sum;
    }

    public Double priseSumForPaperBook(Long bookId) {
        Book bookById = bookRepository.getById(bookId);
        Double sum = 0.0;
        if (bookById.getBookType().equals(BookType.PAPERBOOK)) {
            sum += bookById.getPrice();
        } else
            log.error("The priseSumForPaperBook method does not work with other types of books except paper books");
        return sum;
    }

    public int minus(Long bookId) {
        Book book1 = bookRepository.getById(bookId);
        int numForReturn = 0;
        if (book1.getBookType().equals(BookType.PAPERBOOK)) {
            int numberOfSelected = book1.getPaperBook().getNumberOfSelected();
            numForReturn = numberOfSelected - 1;
            book1.getPaperBook().setNumberOfSelected(numberOfSelected - 1);
            bookRepository.save(book1);
            return (numberOfSelected - 1);
        } else
            log.error("The minus method does not work with other types of books except paper books");
        return numForReturn;
    }

    public int plus(Long bookId) {
        Book book1 = bookRepository.getById(bookId);
        int numForReturn = 0;
        if (book1.getBookType().equals(BookType.PAPERBOOK)) {
            int numberOfSelected = book1.getPaperBook().getNumberOfSelected();
            numForReturn = numberOfSelected + 1;
            book1.getPaperBook().setNumberOfSelected(numberOfSelected + 1);
            bookRepository.save(book1);
            return (numberOfSelected + 1);
        } else
            log.error("The minus method does not work with other types of books except paper books");
        return numForReturn;
    }
}
