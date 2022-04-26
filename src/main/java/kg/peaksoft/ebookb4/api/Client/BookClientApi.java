package kg.peaksoft.ebookb4.api.Client;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.service.ClientService;
import kg.peaksoft.ebookb4.dto.request.Request;
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
    @PostMapping("/basket")
    public ResponseEntity<?> addToBasket(@RequestBody Request request, Authentication authentication){
        return clientService.addBookToBasket(request.getId(), authentication.getName());
    }

    @Operation(summary = "Like a book",description = "Like a book with id")
    @PostMapping("/like")
    public ResponseEntity<?> likeBook(@RequestBody Request request, Authentication authentication){
        return clientService.likeABook(request.getId(), authentication.getName());
    }



}
