package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.db.models.entity.dto.users.ClientOperationDTO;
import kg.peaksoft.ebookb4.db.models.entity.dto.users.ClientRegisterDTO;
import kg.peaksoft.ebookb4.db.models.entity.dto.users.ClientUpdateDTO;
import kg.peaksoft.ebookb4.db.models.entity.dto.response.BookResponse;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.List;

public interface ClientService {

    ResponseEntity<?> register (ClientRegisterDTO clientRegisterDTO, Long number);

    ResponseEntity<?> likeABook(Long bookId, String username);

    ResponseEntity<?> addBookToBasket(Long bookId, String username);

    List<BookResponse> getBooksFromBasket(String id);

    ClientOperationDTO getBooksInBasket(String name);

    @Transactional
    ResponseEntity<?> update(ClientUpdateDTO newClientDTO, String username);

    ClientRegisterDTO getClientDetails(String username);

    ResponseEntity<?> deleteBookFromBasket(Long id, String authentication);

    ResponseEntity<?> cleanBasketOfClientByEmail(String clientEmail);

     ClientOperationDTO sumAfterPromo(String promo,String id);
}
