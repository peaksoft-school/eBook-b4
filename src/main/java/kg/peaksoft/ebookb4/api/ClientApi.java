package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.dto.ClientOperationDTO;
import kg.peaksoft.ebookb4.dto.ClientRegisterDTO;
import kg.peaksoft.ebookb4.dto.ClientUpdateDTO;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.dto.request.Request;
import kg.peaksoft.ebookb4.dto.response.BookResponse;
import kg.peaksoft.ebookb4.dto.response.CardResponse;
import kg.peaksoft.ebookb4.db.service.AdminService;
import kg.peaksoft.ebookb4.db.service.ClientService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/client")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ROLE_CLIENT')")
@Tag(name = "Client API", description = " Client accessible endpoints")
public class ClientApi {

    private final AdminService service;
    private final ClientService clientService;

    @Operation(summary = "Client profile", description = "Client can see all accessible data of client")
    @GetMapping("profile")
    public ClientRegisterDTO getClientDetails(Authentication authentication) {
        return clientService.getClientDetails(authentication.getName());
    }

    @Operation(summary = "Update a client", description = "Update a client profile")
    @PatchMapping("profile/settings")
    public ResponseEntity<?> updateClient(@RequestBody ClientUpdateDTO newClientDTO, Authentication authentication) {
        return clientService.update(newClientDTO, authentication.getName());
    }

    @Operation(summary = "Add to basket", description = "Add a book to basket")
    @PostMapping("basket")
    public ResponseEntity<?> addToBasket(@RequestBody Request request, Authentication authentication) {
        return clientService.addBookToBasket(request.getId(), authentication.getName());
    }

    @Operation(summary = "Like a book", description = "Like a book with id")
    @PostMapping("like")
    public ResponseEntity<?> likeBook(@RequestBody Request request, Authentication authentication) {
        return clientService.likeABook(request.getId(), authentication.getName());
    }

    @Operation(summary = "Delete Book from basket by id", description = "Delete one book from basket when we click cross")
    @DeleteMapping("remove-book-basket/{id}")
    public ResponseEntity<?> deleteBookFromBasket(@PathVariable Long id, Authentication authentication) {
        return clientService.deleteBookFromBasket(id, authentication.getName());
    }

    @Operation(summary = "clean basket ", description = "Delete all books from basket when we click clean all")
    @DeleteMapping("clean-basket")
    public void cleanBasket(Authentication authentication) {
        clientService.cleanBasketOfClientByEmail(authentication.getName());
    }

    @Operation(summary = "Get books", description = "Get all books from clients Basket")
    @GetMapping("client-basket")
    public List<BookResponse> getBooksClientFromBasket(Authentication authentication) {
        return clientService.getBooksFromBasket(authentication.getName());
    }


    @Operation(summary = "Get all sum with discount", description = "Get all sum with discount in cart")
    @GetMapping("get-count-in-card")
    public ClientOperationDTO comparePromoCode(Authentication authentication) {
        return clientService.sumAfterPromo(authentication.getName());
    }

    @Operation(summary = "Place an order", description = "Place an order for all books in the basket")
    @PostMapping("place-an-order")
    public ResponseEntity<?> placeAnOrder(Authentication authentication) {
        return clientService.placeOrder(authentication.getName());
    }

    @Operation(summary = "Get all purchased books", description = "Get all purchased books with username")
    @GetMapping("operation")
    public List<Book> getBook(Authentication authentication) {
        return clientService.operationBook(authentication.getName());
    }

    @Operation(summary = "Plus or minus paper book", description = "Plus or minus paper books when ordering with paper book id ")
    @GetMapping("plus-or-minus/{plsOrMns}/{bookId}/{promoCode}")
    public List<CardResponse> plusOrMinusPaperBook(Authentication authentication,
                                                   @PathVariable String plsOrMns,
                                                   @PathVariable Long bookId,
                                                   @PathVariable String promoCode) {
        return clientService.plusOrMinus(authentication.getName(), plsOrMns, bookId, promoCode);
    }

    @Operation(summary = "Delete client/vendor ", description = "Admin can delete client and vendor!")
    @DeleteMapping({"profile"})
    public ResponseEntity<?> deleteUser(Authentication authentication) {
        return service.deleteById(authentication.getName());
    }

}
