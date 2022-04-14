package kg.peaksoft.ebookb4.service.impl;

import kg.peaksoft.ebookb4.dto.mapper.BookMapper;
import kg.peaksoft.ebookb4.dto.request.BookRequest;
import kg.peaksoft.ebookb4.dto.response.MessageResponse;
import kg.peaksoft.ebookb4.models.bookClasses.AudioBook;
import kg.peaksoft.ebookb4.models.bookClasses.Book;
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
            if (bookRequest.getBookType().name() == BookType.AudioBook.name()) {
                AudioBook audio = new AudioBook();
                audio.setDuration(bookRequest.getAudioBook().getDuration());
                audio.setFragment(bookRequest.getAudioBook().getFragment());
                audio.setUrlOfBookFromCloud(bookRequest.getAudioBook().getUrlOfBookFromCloud());
                book.setAudioBook(audio);
                repository.save(book);
            }
//            if (bookRequest.getBookType().name() == BookType.Ebook.name()) {
//                AudioBook audio = new AudioBook();
//                audio.setDuration(bookRequest.getAudioBook().getDuration());
//                audio.setFragment(bookRequest.getAudioBook().getFragment());
//                audio.setUrlOfBookFromCloud(bookRequest.getAudioBook().getUrlOfBookFromCloud());
//                book.setAudioBook(audio);
//                repository.save(book);
//            }
//            if (bookRequest.getBookType().name() == BookType.PaperBook.name()) {
//                AudioBook audio = new AudioBook();
//                audio.setDuration(bookRequest.getAudioBook().getDuration());
//                audio.setFragment(bookRequest.getAudioBook().getFragment());
//                audio.setUrlOfBookFromCloud(bookRequest.getAudioBook().getUrlOfBookFromCloud());
//                book.setAudioBook(audio);
//                repository.save(book);
//            }

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(String.format("User with id %s doesn't exist!", userId)));
        }


        return ResponseEntity.ok(new MessageResponse(
                String.format("Book with name %s registered successfully!", book.getTitle())));
    }
}
