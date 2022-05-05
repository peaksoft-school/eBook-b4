package kg.peaksoft.ebookb4.api.client;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.service.ClientService;
import kg.peaksoft.ebookb4.db.models.dto.ClientOperationDTO;
import kg.peaksoft.ebookb4.db.models.request.Request;
import kg.peaksoft.ebookb4.db.models.response.BookResponse;
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

    @Operation(summary = "Get books", description = "Get all books from clients Basket")
    @GetMapping("/clientBasket")
    public List<BookResponse> getBooksClientFromBasket(Authentication authentication) {
        return clientService.getBooksFromBasket(authentication.getName());
    }

    @Operation(summary = "Get all counts", description = "Get count of books, discount, sum after we get total")
    @GetMapping("/count")
    public ClientOperationDTO getCountsAlsoTotal(Authentication id){
        return clientService.getBooksInBasket(id.getName());
    }

    @Operation(summary = "Compare the promo code", description = "Compare the promo code if it fits then the person gets a discount ")
    @PostMapping("/{promoCode}")
    public ClientOperationDTO comparePromoCode(@PathVariable String promoCode, Authentication authentication) {
        return clientService.sumAfterPromo(promoCode, authentication.getName());
    }
    
    @PostMapping("/place-an-order")
    public void placeAnOrder( Authentication authentication){
        clientService.pppppOrder( authentication.getName());
    }

}