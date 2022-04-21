package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.repository.PromocodeRepository;
import kg.peaksoft.ebookb4.db.service.BookService;
import kg.peaksoft.ebookb4.dto.request.CustomPageRequest;
import kg.peaksoft.ebookb4.dto.mapper.BookMapper;
import kg.peaksoft.ebookb4.dto.request.BookRequest;
import kg.peaksoft.ebookb4.dto.response.MessageResponse;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import kg.peaksoft.ebookb4.exceptions.NotFoundException;
import kg.peaksoft.ebookb4.db.models.books.AudioBook;
import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.models.books.ElectronicBook;
import kg.peaksoft.ebookb4.db.models.books.PaperBook;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.Genre;
import kg.peaksoft.ebookb4.db.models.enums.Language;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final BookMapper mapper;
    private final UserRepository userRepository;
    private final PromocodeRepository promoRepository;


    @Override
    public ResponseEntity<?> register(BookRequest bookRequest, String username) {

        User user = userRepository.getUser(username).orElseThrow(()->
                new BadRequestException(String.format("User with username %s doesn't exist!",username)));
        Book book = mapper.create(bookRequest);
        if(promoRepository.ifVendorAlreadyCreatedPromo(user)){
            if(book.getDiscount()==null){
                book.setDiscountFromPromo(promoRepository.getActivePromo(user).getDiscount());
            }
        }

        book.setUser(user);
            if (bookRequest.getBookType().name().equals(BookType.AUDIOBOOK.name())) {
                AudioBook audio = new AudioBook();
                audio.setDuration(bookRequest.getAudioBook().getDuration());
                audio.setFragment(bookRequest.getAudioBook().getFragment());
                audio.setUrlOfBookFromCloud(bookRequest.getAudioBook().getUrlOfBookFromCloud());
                book.setAudioBook(audio);

            } else if (bookRequest.getBookType().name().equals(BookType.EBOOK.name())) {
                ElectronicBook ebook = new ElectronicBook();
                ebook.setFragmentOfBook(bookRequest.getElectronicBook().getFragmentOfBook());
                ebook.setNumberOfPages(bookRequest.getElectronicBook().getNumberOfPages());
                ebook.setUrlOfBookFromCloud(bookRequest.getElectronicBook().getUrlOfBookFromCloud());
                book.setElectronicBook(ebook);

            } else {
                PaperBook paperBook = new PaperBook();
                paperBook.setFragmentOfBook(bookRequest.getPaperBook().getFragmentOfBook());
                paperBook.setNumberOfSelected(bookRequest.getPaperBook().getNumberOfSelected());
                paperBook.setNumberOfPages(bookRequest.getPaperBook().getNumberOfPages());
                book.setPaperBook(paperBook);
            }
            user.getVendorAddedBooks().add(book);

        repository.save(book);
        return ResponseEntity.ok(new MessageResponse(
                String.format("%s with name %s registered successfully!", book.getBookType().name(),
                        book.getTitle())));

    }

    @Override
    public Book findByBookId(Long bookId) {
        return repository.findById(bookId)
                .orElseThrow(() -> {
                    throw new NotFoundException(
                            String.format("Book with id = %s does not exists", bookId)
                    );
                });
    }

    @Override
    public List<Book> findAll(int offset, int pageSize) {

        PageRequest pageRequest = PageRequest.of(offset, pageSize, Sort.by("bookId"));

        Page<Book> books = repository.findAll(pageRequest);

        return new CustomPageRequest<>(books).getContent();
    }

    @Override
    public ResponseEntity<?> delete(Long bookId) {
        repository.deleteById(bookId);
        return ResponseEntity.ok(new MessageResponse(
                String.format("Book with id = %s successfully delete!", bookId)));
    }


    @Override
    @Transactional
    public ResponseEntity<?> update(BookRequest newBook, Long bookId) {
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
        Genre newGenre = newBook.getGenre();
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
                break;
            case EBOOK:
                book.getElectronicBook().setFragmentOfBook(newBook.getPaperBook().getFragmentOfBook());
                book.getElectronicBook().setNumberOfPages(newBook.getElectronicBook().getNumberOfPages());
                book.getElectronicBook().setUrlOfBookFromCloud(newBook.getElectronicBook().getUrlOfBookFromCloud());
                break;
            case AUDIOBOOK:
                book.getAudioBook().setDuration(newBook.getAudioBook().getDuration());
                book.getAudioBook().setFragment(newBook.getAudioBook().getFragment());
                book.getAudioBook().setUrlOfBookFromCloud(newBook.getAudioBook().getUrlOfBookFromCloud());
                break;
        }

        return ResponseEntity.ok(new MessageResponse(
                String.format("%s with name %s Update successfully!", book.getBookType().name(),
                        book.getTitle())));
    }

    @Override
    public List<Book> findBooksFromVendor(String username) {
        return repository.findBooksFromVendor(username);
    }




}