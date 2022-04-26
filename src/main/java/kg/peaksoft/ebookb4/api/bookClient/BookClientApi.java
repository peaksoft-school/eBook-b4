package kg.peaksoft.ebookb4.api.bookClient;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.service.ClientService;
import kg.peaksoft.ebookb4.dto.request.BookRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> addToBasket(@RequestBody BookRequestDto request, Authentication authentication){
        return clientService.addBookToBasket(request.getId(), authentication.getName());
    }

    @PostMapping("/like")
    public ResponseEntity<?> likeBook(@RequestBody BookRequestDto request, Authentication authentication){
        return clientService.likeABook(request.getId(), authentication.getName());
    }
}