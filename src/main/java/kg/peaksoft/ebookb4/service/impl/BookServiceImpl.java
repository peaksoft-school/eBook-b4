package kg.peaksoft.ebookb4.service.impl;

import kg.peaksoft.ebookb4.dto.mapper.BookMapper;
import kg.peaksoft.ebookb4.dto.request.BookRequest;
import kg.peaksoft.ebookb4.dto.response.MessageResponse;
import kg.peaksoft.ebookb4.models.bookClasses.AudioBook;
import kg.peaksoft.ebookb4.models.bookClasses.Book;
import kg.peaksoft.ebookb4.models.bookClasses.ElectronicBook;
import kg.peaksoft.ebookb4.models.bookClasses.PaperBook;
import kg.peaksoft.ebookb4.models.enums.BookType;
import kg.peaksoft.ebookb4.repository.BookRepository;
import kg.peaksoft.ebookb4.repository.UserRepository;
import kg.peaksoft.ebookb4.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final BookMapper mapper;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> register(BookRequest bookRequest, Long userId) {

        Book book = mapper.create(bookRequest);
        try {
            book.setUser(userRepository.getById(userId));
            if (bookRequest.getBookType().name().equals(BookType.AudioBook.name())) {
                System.out.println("Hello AudioBook");
                AudioBook audio = new AudioBook();
                audio.setDuration(bookRequest.getAudioBook().getDuration());
                audio.setFragment(bookRequest.getAudioBook().getFragment());
                audio.setUrlOfBookFromCloud(bookRequest.getAudioBook().getUrlOfBookFromCloud());
                book.setAudioBook(audio);

            } else if (bookRequest.getBookType().name().equals(BookType.Ebook.name())) {
                System.out.println("Hello EBook");
                ElectronicBook ebook = new ElectronicBook();
                ebook.setPublishingHouse(bookRequest.getElectronicBook().getPublishingHouse());
                ebook.setFragmentOfBook(bookRequest.getElectronicBook().getFragmentOfBook());
                ebook.setNumberOfPages(bookRequest.getElectronicBook().getNumberOfPages());
                ebook.setUrlOfBookFromCloud(bookRequest.getElectronicBook().getUrlOfBookFromCloud());
                book.setElectronicBook(ebook);

            } else {
                System.out.println("Hello PaperBook");
                PaperBook paperBook = new PaperBook();
                paperBook.setPublishingHouse(bookRequest.getPaperBook().getPublishingHouse());
                paperBook.setFragmentOfBook(bookRequest.getPaperBook().getFragmentOfBook());
                paperBook.setNumberOfSelected(bookRequest.getPaperBook().getNumberOfSelected());
                paperBook.setNumberOfPages(bookRequest.getPaperBook().getNumberOfPages());
                book.setPaperBook(paperBook);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(String.format("User with id %s doesn't exist!", userId)));
        }

                repository.save(book);
        return ResponseEntity.ok(new MessageResponse(
                String.format("%s with name %s registered successfully!",book.getBookType().name(),
                        book.getTitle())));

    }
}