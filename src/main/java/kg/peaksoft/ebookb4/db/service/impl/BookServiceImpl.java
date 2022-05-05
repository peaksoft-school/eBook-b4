package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.entity.Genre;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.db.models.entity.User;
import kg.peaksoft.ebookb4.db.repository.GenreRepository;
import kg.peaksoft.ebookb4.db.repository.PromocodeRepository;
import kg.peaksoft.ebookb4.db.service.BookService;
import kg.peaksoft.ebookb4.db.models.request.CustomPageRequest;
import kg.peaksoft.ebookb4.db.models.mappers.BookMapper;
import kg.peaksoft.ebookb4.db.models.dto.BookDTO;
import kg.peaksoft.ebookb4.db.models.response.MessageResponse;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import kg.peaksoft.ebookb4.exceptions.NotFoundException;
import kg.peaksoft.ebookb4.db.models.entity.AudioBook;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.entity.ElectronicBook;
import kg.peaksoft.ebookb4.db.models.entity.PaperBook;
import kg.peaksoft.ebookb4.db.models.enums.BookType;

import kg.peaksoft.ebookb4.db.models.enums.Language;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final BookMapper mapper;
    private final UserRepository userRepository;
    private final PromocodeRepository promoRepository;
    private final GenreRepository genreRepository;

    @Override
    public ResponseEntity<?> saveBook(BookDTO bookDTO, String username) {
        User user = userRepository.getUser(username)
                .orElseThrow(() -> {
                    log.error("Vendor with name ={} does not exists", username);
                    throw new BadRequestException(
                            String.format("User with username %s doesn't exist!", username)
                    );
                });
        Book book = mapper.create(bookDTO);
        if (promoRepository.ifVendorAlreadyCreatedPromo(user, LocalDate.now())) {
            log.info("check promo book");
            if (book.getDiscount() == null) {
                book.setDiscountFromPromo(promoRepository.getActivePromo(user).getDiscount());
            }
        }

        book.setDateOfRegister(LocalDate.now());
        book.setEndOfTheNewTerm(LocalDate.now().plusDays(30));

        book.setUser(user);
        if (bookDTO.getBookType().name().equals(BookType.AUDIOBOOK.name())) {
            AudioBook audio = new AudioBook();
            audio.setDuration(bookDTO.getAudioBook().getDuration());
            audio.setFragment(bookDTO.getAudioBook().getFragment());
            audio.setUrlOfBookFromCloud(bookDTO.getAudioBook().getUrlOfBookFromCloud());
            book.setAudioBook(audio);
            log.info("Save audio book");

        } else if (bookDTO.getBookType().name().equals(BookType.EBOOK.name())) {
            ElectronicBook ebook = new ElectronicBook();
            ebook.setFragmentOfBook(bookDTO.getElectronicBook().getFragmentOfBook());
            ebook.setNumberOfPages(bookDTO.getElectronicBook().getNumberOfPages());
            ebook.setUrlOfBookFromCloud(bookDTO.getElectronicBook().getUrlOfBookFromCloud());
            book.setElectronicBook(ebook);
            log.info("Save electronic book");
        } else {
            PaperBook paperBook = new PaperBook();
            paperBook.setFragmentOfBook(bookDTO.getPaperBook().getFragmentOfBook());
            paperBook.setNumberOfSelected(bookDTO.getPaperBook().getNumberOfSelected());
            paperBook.setNumberOfPages(bookDTO.getPaperBook().getNumberOfPages());
            book.setPaperBook(paperBook);
            log.info("Save paper book ");
        }
        user.getVendorAddedBooks().add(book);
        repository.save(book);
        log.info("Save book works");
        return ResponseEntity.ok(new MessageResponse(
                String.format("%s with name %s registered successfully!", book.getBookType().name(),
                        book.getTitle())));

    }

    @Override
    public Book findByBookId(Long bookId) {
        return repository.findById(bookId)
                .orElseThrow(() -> {
                    log.error("Book with id = {} does not exists",bookId);
                    throw new NotFoundException(
                            String.format("Book with id = %s does not exists", bookId)
                    );
                });
    }

    @Override
    public ResponseEntity<?> delete(Long bookId) {
        repository.deleteById(bookId);
        log.info("delete book works");
        return ResponseEntity.ok(new MessageResponse(
                String.format("Book with id = %s successfully delete!", bookId)));
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(BookDTO newBook, Long bookId, Long genreId) {
        Book book = findByBookId(bookId);

        String bookName = book.getTitle();
        String newBookName = newBook.getTitle();
        if (!Objects.equals(bookName, newBookName)) {
            book.setTitle(newBookName);
        }
        String bookAuthor = book.getAuthorFullName();
        String newBookAuthor = newBook.getAuthorFullName();
        if (!Objects.equals(bookAuthor, newBookAuthor)) {
            book.setAuthorFullName(newBookAuthor);
        }
        String about = book.getAboutBook();
        String newAbout = newBook.getAboutBook();
        if (!Objects.equals(about, newAbout)) {
            book.setAboutBook(newAbout);
        }
        LocalDate date = book.getYearOfIssue();
        LocalDate newDate = newBook.getYearOfIssue();
        if (!Objects.equals(date, newDate)) {
            book.setYearOfIssue(newDate);
        }
        Integer discount = book.getDiscount();
        Integer newDiscount = newBook.getDiscount();
        if (!Objects.equals(discount, newDiscount)) {
            book.setDiscount(newDiscount);
        }
        Double price = book.getPrice();
        Double newPrice = newBook.getPrice();
        if (!Objects.equals(price, newPrice) && newPrice.intValue() > 0) {
            book.setPrice(newPrice);
        }
        Boolean isBestSeller = book.getIsBestSeller();
        Boolean newIsBestSeller = newBook.getIsBestSeller();
        if (!Objects.equals(isBestSeller, newIsBestSeller)) {
            book.setIsBestSeller(newIsBestSeller);
        }
        Language language = book.getLanguage();
        Language newLanguage = newBook.getLanguage();
        if (!Objects.equals(language, newLanguage)) {
            book.setLanguage(newLanguage);
        }
        Genre genre = book.getGenre();
        Genre newGenre = genreRepository.getById(newBook.getGenreId());
        if (!Objects.equals(genre, newGenre)) {
            book.setGenre(newGenre);
        }
        BookType bookType = book.getBookType();
        BookType newBookType = newBook.getBookType();
        if (!Objects.equals(bookType, newBookType)) {
            book.setBookType(newBookType);
        }

        switch (newBook.getBookType()) {
            case PAPERBOOK:
                book.getPaperBook().setFragmentOfBook(newBook.getPaperBook().getFragmentOfBook());
                book.getPaperBook().setNumberOfPages(newBook.getPaperBook().getNumberOfPages());
                book.getPaperBook().setNumberOfSelected(newBook.getPaperBook().getNumberOfSelected());
                log.info("Update paper book");
                break;
            case EBOOK:
                book.getElectronicBook().setFragmentOfBook(newBook.getPaperBook().getFragmentOfBook());
                book.getElectronicBook().setNumberOfPages(newBook.getElectronicBook().getNumberOfPages());
                book.getElectronicBook().setUrlOfBookFromCloud(newBook.getElectronicBook().getUrlOfBookFromCloud());
                log.info("Update ebook");
                break;
            case AUDIOBOOK:
                book.getAudioBook().setDuration(newBook.getAudioBook().getDuration());
                book.getAudioBook().setFragment(newBook.getAudioBook().getFragment());
                book.getAudioBook().setUrlOfBookFromCloud(newBook.getAudioBook().getUrlOfBookFromCloud());
                log.info("Update audio book");
                break;
        }
        log.info("Update book works");
        return ResponseEntity.ok(new MessageResponse(
                String.format("%s with name %s Update successfully!", book.getBookType().name(),
                        book.getTitle())));
    }

    @Override
    public List<Book> findBooksFromVendor(Integer offset, int pageSize, String username) {

        List<Book> books = repository.findBooksFromVendor(username);

        log.info("founded {} accepted books", books.size());
        Pageable paging = PageRequest.of(offset, pageSize);
        int start = Math.min((int) paging.getOffset(), books.size());
        int end = Math.min((start + paging.getPageSize()), books.size());
        Page<Book> pages = new PageImpl<>(books.subList(start, end), paging, books.size());
        return new CustomPageRequest<>(pages).getContent();
    }

    @Override
    public List<Book> findBooksFromVendorInFavorites(Integer offset, int pageSize, String username) {
        List<Book> likedBooksFromVendor = repository.findLikedBooksFromVendor(username);
        Pageable paging = PageRequest.of(offset, pageSize);
        int start = Math.min((int) paging.getOffset(), likedBooksFromVendor.size());
        int end = Math.min((start + paging.getPageSize()), likedBooksFromVendor.size());
        Page<Book> pages = new PageImpl<>(likedBooksFromVendor.subList(start, end), paging, likedBooksFromVendor.size());
        log.info("likes book = {}",likedBooksFromVendor.size());
        return new CustomPageRequest<>(pages).getContent();
    }

    @Override
    public List<Book> findBooksFromVendorAddedToBasket(Integer offset, int pageSize, String username) {

        List<Book> booksWithBasket = repository.findBooksFromVendorAddedToBasket(username);

        Pageable paging = PageRequest.of(offset, pageSize);
        int start = Math.min((int) paging.getOffset(), booksWithBasket.size());
        int end = Math.min((start + paging.getPageSize()), booksWithBasket.size());
        Page<Book> pages = new PageImpl<>(booksWithBasket.subList(start, end), paging, booksWithBasket.size());
        log.info("{} Books from vendor added to basket ",booksWithBasket.size());
        return new CustomPageRequest<>(pages).getContent();
    }

    @Override
    public List<Book> findBooksFromVendorWithDiscount(Integer offset, int pageSize, String username) {
        List<Book> booksWithDiscount = repository.findBooksFromVendorWithDiscount(username);
        Pageable paging = PageRequest.of(offset, pageSize);
        int start = Math.min((int) paging.getOffset(), booksWithDiscount.size());
        int end = Math.min((start + paging.getPageSize()), booksWithDiscount.size());
        Page<Book> pages = new PageImpl<>(booksWithDiscount.subList(start, end), paging, booksWithDiscount.size());
        log.info("find books from discount");
        return new CustomPageRequest<>(pages).getContent();
    }

    @Override
    public List<Book> findBooksFromVendorCancelled(Integer offset, int pageSize, String username,
                                                   RequestStatus requestStatus) {
        List<Book> booksWithCancel = repository.findBooksFromVendorWithCancel(username, requestStatus);
        Pageable paging = PageRequest.of(offset, pageSize);
        int start = Math.min((int) paging.getOffset(), booksWithCancel.size());
        int end = Math.min((start + paging.getPageSize()), booksWithCancel.size());
        Page<Book> pages = new PageImpl<>(booksWithCancel.subList(start, end), paging, booksWithCancel.size());
        log.info("vendor books {} cancelled",booksWithCancel.size());
        return new CustomPageRequest<>(pages).getContent();
    }

    @Override
    public List<Book> findBooksFromVendorInProcess(Integer offset, int pageSize, String username,
                                                   RequestStatus requestStatus) {
        List<Book> booksInProgress = repository.findBooksFromVendorInProgress(username, requestStatus);
        Pageable paging = PageRequest.of(offset, pageSize);
        int start = Math.min((int) paging.getOffset(), booksInProgress.size());
        int end = Math.min((start + paging.getPageSize()), booksInProgress.size());
        Page<Book> pages = new PageImpl<>(booksInProgress.subList(start, end), paging, booksInProgress.size());
        log.info("Vendor book = {} in process",booksInProgress.size());
        return new CustomPageRequest<>(pages).getContent();
    }
}