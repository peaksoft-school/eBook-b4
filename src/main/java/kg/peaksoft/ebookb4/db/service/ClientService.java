package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.dto.dto.ClientDTO;
import org.springframework.http.ResponseEntity;

public interface ClientService {



    ResponseEntity<?> register (ClientDTO clientDTO, Long number);

    ResponseEntity<?> likeABook(Long bookId, String username);

    ResponseEntity<?> addBookToBasket(Long bookId, String username);

}
