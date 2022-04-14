package kg.peaksoft.ebookb4.service;

import kg.peaksoft.ebookb4.dto.request.BookRequest;
import org.springframework.http.ResponseEntity;

public interface BookService {
    ResponseEntity<?> register(BookRequest bookRequest, Long userId);

}
