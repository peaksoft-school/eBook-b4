package kg.peaksoft.ebookb4.api.bookClient;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 23/4/22
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/client/books")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_CLIENT')")
@Tag(name = "Books client",description = "client manipulations ...")
public class BookClientApi {

    private ClientService clientService;
    @Operation(summary = "add to basket",description = "add a book to basket")
    @PostMapping("/basket/{bookId}")
    public ResponseEntity<?> addToBasket(@PathVariable Long bookId, Authentication authentication){
        return clientService.addBookToBasket(bookId, authentication.getName());

    }

    @PostMapping("/like/{bookId}")
    public ResponseEntity<?> likeBook(@PathVariable Long bookId, Authentication authentication){
        return clientService.likeABook(bookId, authentication.getName());
    }
}
