package kg.peaksoft.ebookb4.api.clientApi;

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
@CrossOrigin
@RestController
@RequestMapping("/api/books/client")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_CLIENT')")
@Tag(name = "Books vendor",description = "crud operations ...")
public class BookClientApi {

    private ClientService clientService;
    @Operation(summary = "add to basket",description = "add a book to basket")
    @PostMapping("/addToBasket/{bookId}")
    public ResponseEntity<?> addToBasket(@PathVariable Long bookId, Authentication authentication){
        return clientService.addBookToBasket(bookId, authentication.getName());

    }

    @PostMapping("/like-book/{bookId}")
    public ResponseEntity<?> likeBook(@PathVariable Long bookId, Authentication authentication){
        return clientService.likeABook(bookId, authentication.getName());
    }


}
