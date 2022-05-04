package kg.peaksoft.ebookb4.api.Client;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.service.ClientService;
import kg.peaksoft.ebookb4.dto.dto.users.ClientOperationDTO;
import kg.peaksoft.ebookb4.dto.request.Request;
import kg.peaksoft.ebookb4.dto.response.BookResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/client/books")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_CLIENT')")
@Tag(name = "Books client",description = "client manipulations ...")
public class BookClientApi {

    private ClientService clientService;

    @Operation(summary = "add to basket",description = "add a book to basket")
    @PostMapping("/basket")
    public ResponseEntity<?> addToBasket(@RequestBody Request request, Authentication authentication){
        return clientService.addBookToBasket(request.getId(), authentication.getName());
    }

    @GetMapping("/clientBasket/{clientId}")
    public List<BookResponse> getBooksClientFromBasket(@PathVariable Long clientId) {
        return clientService.getBooksFromBasket(clientId);
    }

    @Operation(summary = "Like a book",description = "Like a book with id")
    @PostMapping("/like")
    public ResponseEntity<?> likeBook(@RequestBody Request request, Authentication authentication){
        return clientService.likeABook(request.getId(), authentication.getName());
    }

    @Operation(summary = "Delete Book from basket by id",description = "Delete one book from basket when we click cross")
    @DeleteMapping("/remove-book-basket/{id}")
    public ResponseEntity<?> deleteBookFromBasket(@PathVariable Long id, Authentication authentication) {
        return clientService.deleteBookFromBasket(id,authentication.getName());
    }

    @Operation(summary = "clean basket ",description = "Delete all books from basket when we click clean all")
    @DeleteMapping("/clean")
    public void cleanBasket(Authentication authentication) {
        clientService.cleanBasketOfClientByEmail(authentication.getName());
    }



    @GetMapping("/gets")
    public ClientOperationDTO gets(ClientOperationDTO dto){
        return clientService.operationClient(dto);
    }
}