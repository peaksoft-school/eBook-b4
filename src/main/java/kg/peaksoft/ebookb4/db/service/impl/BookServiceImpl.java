package kg.peaksoft.ebookb4.db.service.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import kg.peaksoft.ebookb4.aws.AwsCredentials;
import kg.peaksoft.ebookb4.db.models.booksClasses.FileInformation;
import kg.peaksoft.ebookb4.db.models.dto.BookDTO;
import kg.peaksoft.ebookb4.db.models.entity.*;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.enums.Language;
import kg.peaksoft.ebookb4.db.models.enums.RequestStatus;
import kg.peaksoft.ebookb4.db.models.mappers.BookMapper;
import kg.peaksoft.ebookb4.db.models.request.CustomPageRequest;
import kg.peaksoft.ebookb4.db.models.response.BookResponse;
import kg.peaksoft.ebookb4.db.models.response.BookResponseAfterSaved;
import kg.peaksoft.ebookb4.db.models.response.CountForAdmin;
import kg.peaksoft.ebookb4.db.models.response.MessageResponse;
import kg.peaksoft.ebookb4.db.repository.*;
import kg.peaksoft.ebookb4.db.service.BookService;
import kg.peaksoft.ebookb4.db.service.PromoService;
import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import kg.peaksoft.ebookb4.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final PromoService promoService;
    private final AmazonS3Client awsS3Client;



    @Override
    public BookResponseAfterSaved saveBook(BookDTO bookDTO, String username) {
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
        FileInformation newFileInformation = new FileInformation();
        book.setFileInformation(newFileInformation);
        if (bookDTO.getBookType().name().equals(BookType.AUDIOBOOK.name())) {
            AudioBook audio = new AudioBook();
            audio.setHour(bookDTO.getAudioBook().getHour());
            audio.setMinute(bookDTO.getAudioBook().getMinute());
            audio.setSecond(bookDTO.getAudioBook().getSecond());
            book.setAudioBook(audio);
            log.info("Save audio book");

        } else if (bookDTO.getBookType().name().equals(BookType.EBOOK.name())) {
            ElectronicBook ebook = new ElectronicBook();
            ebook.setFragmentOfBook(bookDTO.getElectronicBook().getFragmentOfBook());
            ebook.setNumberOfPages(bookDTO.getElectronicBook().getNumberOfPages());
            book.setElectronicBook(ebook);
            log.info("Save electronic book");
        } else {
            PaperBook paperBook = new PaperBook();
            paperBook.setFragmentOfBook(bookDTO.getPaperBook().getFragmentOfBook());
            paperBook.setNumberOfSelected(bookDTO.getPaperBook().getNumberOfSelected());
            paperBook.setNumberOfSelectedCopy(bookDTO.getPaperBook().getNumberOfSelected());
            paperBook.setNumberOfPages(bookDTO.getPaperBook().getNumberOfPages());
            book.setPaperBook(paperBook);
            log.info("Save paper book ");
        }
        user.getVendorAddedBooks().add(book);
        repository.save(book);
        log.info("Save book works");
        BookResponseAfterSaved bookResponse = new BookResponseAfterSaved();
        bookResponse.setId(book.getBookId());
        bookResponse.setName(book.getTitle());
        bookResponse.setBookType(book.getBookType());
        bookResponse.setMessage("Book was successfully saved");
        return bookResponse;

    }

    @Override
    public Book findByBookId(Long bookId) {
        promoService.checkPromos();
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
        Book bookById = repository.getById(bookId);
        if (bookById.getFileInformation().getKeyOfFirstPhoto() != null) {
            deleteFile(bookById.getFileInformation().getKeyOfFirstPhoto());
        }
        if (bookById.getFileInformation().getKeyOfSecondPhoto() != null) {
            deleteFile(bookById.getFileInformation().getKeyOfSecondPhoto());
        }
        if (bookById.getFileInformation().getKeyOfThirdPhoto() != null) {
            deleteFile(bookById.getFileInformation().getThirdPhoto());
        }
        if (bookById.getFileInformation().getKeyOfBookFile() != null) {
            deleteFile(bookById.getFileInformation().getKeyOfBookFile());
        }
        if (bookById.getBookType().equals(BookType.AUDIOBOOK)) {
            if (bookById.getAudioBook().getKeyOfFragment() != null) {
                deleteFile(bookById.getAudioBook().getKeyOfFragment());
            }
        }
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
        FileInformation fileInformation = book.getFileInformation();
        FileInformation newFileInformation = newBook.getFileInformation();
        if (!Objects.equals(fileInformation, newFileInformation)){
            if (!Objects.equals(fileInformation.getFirstPhoto(), newFileInformation.getFirstPhoto()) && newFileInformation.getFirstPhoto() != null){
                deleteFile(fileInformation.getKeyOfFirstPhoto());
            }if (!Objects.equals(fileInformation.getSecondPhoto(),newFileInformation.getSecondPhoto()) && newFileInformation.getSecondPhoto() != null){
                deleteFile(fileInformation.getKeyOfSecondPhoto());
            }if(!Objects.equals(fileInformation.getBookFile(),newFileInformation.getBookFile()) && newFileInformation.getBookFile() != null){
                deleteFile(fileInformation.getKeyOfBookFile());
            }
            book.setFileInformation(newFileInformation);
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
        int date = book.getYearOfIssue();
        int newDate = newBook.getYearOfIssue();
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
                log.info("Update ebook");
                break;
            case AUDIOBOOK:
                book.getAudioBook().setHour(newBook.getAudioBook().getHour());
                book.getAudioBook().setMinute(newBook.getAudioBook().getMinute());
                book.getAudioBook().setSecond(newBook.getAudioBook().getSecond());
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
        promoService.checkPromos();
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

    @Override
    public List<BookResponse> getBooksSold(String name , ERole role) {
        return repository.getVendorBooksSold(name, role);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }


    public void deleteFile(String keyName) {
        final DeleteObjectRequest deleteObjectRequest = new
                DeleteObjectRequest(AwsCredentials.AWS_BUCKET_NAME.getAwsCredentials(), keyName);
        awsS3Client.deleteObject(deleteObjectRequest);
        log.info("Successfully deleted");
    }

    @Override
    public CountForAdmin getCountOfVendorBooks(String name) {
        List<Book> booksFromVendor = repository.findBooksFromVendor(name);
        Integer integer = booksFromVendor.size();
        CountForAdmin count = new CountForAdmin();
        count.setCountOfPages(countOfPages(integer));
        count.setAll(integer);
        return count;
    }

    public Integer countOfPages(Integer books) {
        int count = 1;
        int size = books;
        for (int i = 0; i < size; i++) {
            if (size - 16 >= 0) {
                size -= 16;
                count++;
            }
        }
        return count;
    }

}