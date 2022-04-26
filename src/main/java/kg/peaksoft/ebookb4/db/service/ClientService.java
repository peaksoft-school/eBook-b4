package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.dto.dto.users.ClientRegisterDTO;
import kg.peaksoft.ebookb4.dto.dto.users.ClientUpdateDTO;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;

public interface ClientService {



    ResponseEntity<?> register (ClientRegisterDTO clientRegisterDTO, Long number);

    ResponseEntity<?> likeABook(Long bookId, String username);

    ResponseEntity<?> addBookToBasket(Long bookId, String username);

    @Transactional
    ResponseEntity<?> update(ClientUpdateDTO newClientDTO, String username);

    ClientRegisterDTO getClientDetails(String username);

    ResponseEntity<?> deleteBookFromBasket(Long id, String authentication);

    ResponseEntity<?> cleanBasketOfClientByEmail(String clientEmail);
}
