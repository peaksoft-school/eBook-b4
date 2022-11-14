package kg.peaksoft.ebookb4.db.models.response;

import kg.peaksoft.ebookb4.db.models.booksClasses.PromoCode;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.entity.User;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.db.repository.PromocodeRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;


@Slf4j
@Component
@AllArgsConstructor
public class CardOperationResponse {
    private BookRepository bookRepository;

    private UserRepository userRepository;

    private PromocodeRepository promocodeRepository;

    public List<CardResponse> create(String name, List<CardResponse> cardResponseList, String plusOrMinus, Long bookId, String promoCode) {
        User user = userRepository.findByEmail(name)
                .orElseThrow(() -> new BadRequestException(
                        "Client with email = " + name + " does not exists"
                ));
        List<Book> bookListFromBasketOfClient = bookRepository.findBasketByClientId(name);
        for (int i = 0; i < cardResponseList.size(); i++) {
            cardResponseList.get(i).setBookId(bookListFromBasketOfClient.get(i).getBookId());
            cardResponseList.get(i).setTitle(bookListFromBasketOfClient.get(i).getTitle());
            cardResponseList.get(i).setAuthorFullName(bookListFromBasketOfClient.get(i).getAuthorFullName());
            cardResponseList.get(i).setAboutBook(bookListFromBasketOfClient.get(i).getAboutBook());
            cardResponseList.get(i).setPublishingHouse(bookListFromBasketOfClient.get(i).getPublishingHouse());
            cardResponseList.get(i).setYearOfIssue(bookListFromBasketOfClient.get(i).getYearOfIssue());
            cardResponseList.get(i).setPrice(bookListFromBasketOfClient.get(i).getPrice());
            if (bookListFromBasketOfClient.get(i).getDiscount() == null) {
                cardResponseList.get(i).setSumAfterDiscount(bookListFromBasketOfClient.get(i).getPrice() - (bookListFromBasketOfClient.get(i).getDiscountFromPromo() * bookListFromBasketOfClient.get(i).getPrice()) / 100);
            } else if (bookListFromBasketOfClient.get(i).getDiscountFromPromo() == null) {
                cardResponseList.get(i).setSumAfterDiscount(bookListFromBasketOfClient.get(i).getPrice() - (bookListFromBasketOfClient.get(i).getDiscount() * bookListFromBasketOfClient.get(i).getPrice()) / 100);
            } else cardResponseList.get(i).setSumAfterDiscount(null);
            cardResponseList.get(i).setDiscount(bookListFromBasketOfClient.get(i).getDiscount());
            cardResponseList.get(i).setPromoDiscount(bookListFromBasketOfClient.get(i).getDiscountFromPromo());

            if (bookListFromBasketOfClient.get(i).getBookType().equals(BookType.PAPERBOOK) && bookListFromBasketOfClient.get(i).getPaperBook().getNumberOfSelected() > 0) {
                if (bookListFromBasketOfClient.get(i).getBookId().equals(bookId)) {
                    if (plusOrMinus.equals("plus")) {
                        if (bookListFromBasketOfClient.get(i).getPaperBook().getNumberOfSelected() >= 0) {
                            Integer numberOfSelectedCopy = bookListFromBasketOfClient.get(i).getPaperBook().getNumberOfSelectedCopy();
                            int minus = minus(bookId);
                            int i1 = numberOfSelectedCopy - minus;
                            cardResponseList.get(i).setCountOfPaperBook(i1);
                            cardResponseList.get(i).setCountOfBooksInTotal((i1));

                            user.getPlaceCounts().setCountOfPaperBookPB(user.getPlaceCounts().getCountOfPaperBookPB() + 1);
                            user.getPlaceCounts().setDiscountPB(user.getPlaceCounts().getDiscountPB() + discountForPaperBook(bookId));
                            user.getPlaceCounts().setSumAfterPromoPB(user.getPlaceCounts().getSumAfterPromoPB() + sumAfterPromoPaperBook(promoCode, bookId));
                            user.getPlaceCounts().setTotalPB(user.getPlaceCounts().getTotalPB() + totalForPaperBook(bookId));
                            user.getPlaceCounts().setSumPB(user.getPlaceCounts().getSumPB() + priseSumForPaperBook(bookId));
                            bookRepository.save(bookListFromBasketOfClient.get(i));
                        } else {
                            log.info("the paper books under this id = {} are over", bookListFromBasketOfClient.get(i).getBookId());
                            continue;
                        }
                    } else if (plusOrMinus.equals("minus")) {
                        Integer numberOfSelectedCopy = bookListFromBasketOfClient.get(i).getPaperBook().getNumberOfSelectedCopy();
                        System.out.println(numberOfSelectedCopy);
                        int plus = plus(bookId);
                        int abs = Math.abs(plus - numberOfSelectedCopy);
                        cardResponseList.get(i).setCountOfPaperBook(abs);
                        cardResponseList.get(i).setCountOfBooksInTotal((abs));

                        user.getPlaceCounts().setCountOfPaperBookPB(user.getPlaceCounts().getCountOfPaperBookPB() - 1);
                        user.getPlaceCounts().setDiscountPB(user.getPlaceCounts().getDiscountPB() - (discountForPaperBook(bookId)));
                        user.getPlaceCounts().setSumAfterPromoPB(user.getPlaceCounts().getSumAfterPromoPB() - sumAfterPromoPaperBook(promoCode, bookId));
                        user.getPlaceCounts().setTotalPB(user.getPlaceCounts().getTotalPB() - totalForPaperBook(bookId));
                        user.getPlaceCounts().setSumPB(user.getPlaceCounts().getSumPB() - priseSumForPaperBook(bookId));
                        bookRepository.save(bookListFromBasketOfClient.get(i));
                    }
                }
            } else {
                cardResponseList.get(i).setCountOfBooksInTotal(count(bookListFromBasketOfClient));

                user.getPlaceCounts().setCountOfBooksInTotal(count(bookListFromBasketOfClient));
                user.getPlaceCounts().setDiscount(discount(bookListFromBasketOfClient));
                List<PromoCode> allPromos = promocodeRepository.findAll();
                for (PromoCode promo : allPromos) {
                    if (!Objects.equals(promoCode, promo.getPromoCode())) {
                        user.getPlaceCounts().setSumAfterPromo(user.getPlaceCounts().getSumAfterPromo());
                    } else {
                        user.getPlaceCounts().setSumAfterPromo(sumAfterPromo(bookListFromBasketOfClient, promoCode));
                        System.out.println(sumAfterPromo(bookListFromBasketOfClient, promoCode));
                    }
                }
                user.getPlaceCounts().setSum(priseSum(bookListFromBasketOfClient));
                user.getPlaceCounts().setTotal(total(bookListFromBasketOfClient));
                bookRepository.save(bookListFromBasketOfClient.get(i));
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

    public Boolean checkPromo(String promo) {
        List<PromoCode> promocode = promocodeRepository.findAll();
        for (PromoCode promoCode1 : promocode) {
            if (promoCode1.getPromoCode().equals(promo)) {
                if (promoCode1.getIsActive().equals(true)) {
                    return true;
                }
                log.error("this {} promo not found", promo);
            }
            log.error("this {} promo is not active", promocode);
        }
        return false;
    }

    public Double sumAfterPromoPaperBook(String promo, Long id) {
        Book book = bookRepository.getById(id);
        Double sum = 0.0;
        if (book.getDiscountFromPromo() != null) {
            if (checkPromo(promo)) {
                sum += (book.getPrice() * book.getDiscountFromPromo()) / 100;
            } else
                log.info("Your promo code is not suitable");
        }
        return sum;
    }

    public Double sumAfterPromo(List<Book> list, String promo) {
        Double sum = 0.0;
        for (Book book : list) {
            if (book.getBookType().equals(BookType.EBOOK) || book.getBookType().equals(BookType.AUDIOBOOK)) {
                if (book.getDiscountFromPromo() != null) {
                    if (checkPromo(promo)) {
                        sum += (book.getPrice() * book.getDiscountFromPromo()) / 100;
                    } else
                        log.info("Your promo code is not suitable");
                }
                continue;
            }
        }
        return sum;
    }
}
