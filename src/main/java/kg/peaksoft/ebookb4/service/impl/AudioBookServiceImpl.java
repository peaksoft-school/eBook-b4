package kg.peaksoft.ebookb4.service.impl;

import kg.peaksoft.ebookb4.dto.mapper.AudioBookMapper;
import kg.peaksoft.ebookb4.dto.request.AudioBookRequest;
import kg.peaksoft.ebookb4.dto.request.SignupRequestClient;
import kg.peaksoft.ebookb4.dto.response.MessageResponse;
import kg.peaksoft.ebookb4.models.bookClasses.Book;
import kg.peaksoft.ebookb4.repository.BookRepository;
import kg.peaksoft.ebookb4.repository.UserRepository;
import kg.peaksoft.ebookb4.service.AudioBookService;
import kg.peaksoft.ebookb4.service.VendorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@AllArgsConstructor
public class AudioBookServiceImpl implements AudioBookService {

    private final BookRepository repository;
    private final AudioBookMapper mapper;
//    private final VendorService vendorService;
//    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> register(AudioBookRequest audioBookRequest) {


        Book book = mapper.create(audioBookRequest);
//        book.setUser(userRepository.get);
        repository.save(book);

        return ResponseEntity.ok(new MessageResponse(
                String.format("User with name %s registered successfully!", book.getTitle())));
    }
}
