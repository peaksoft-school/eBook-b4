package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/books/client")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_CLIENT')")
@Tag(name = "Books",description = "crud operations")
public class ClientApi {

    private ClientService clientService;

    @PostMapping("/like-book/{bookId}")
    public ResponseEntity<?> likeBook(@PathVariable Long bookId, Authentication authentication){
        return clientService.likeABook(bookId, authentication.getName());
    }

}
