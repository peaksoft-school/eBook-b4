package kg.peaksoft.ebookb4.api.admin;

import io.swagger.v3.oas.annotations.Operation;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.response.BookResponse;
import kg.peaksoft.ebookb4.db.models.response.ClientResponse;
import kg.peaksoft.ebookb4.db.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/admin/client")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ClientAdminApi {

    private AdminService service;

    @Operation(summary = "Get all client",
            description = "Get all client ")
    @GetMapping("/")
    public List<ClientResponse> getAllClient() {
        return service.findAllClient();
    }

    @Operation(summary = "Get client by id")
    @GetMapping("/{id}")
    public ClientResponse getClientById(@PathVariable Long id) {
        return service.getClientById(id);
    }

    @GetMapping("/basket/{clientId}")
    public List<BookResponse> getBooksClientFromBasket(@PathVariable Long clientId) {
        return service.getBooksFromBasket(clientId);
    }

    @GetMapping("/favorites/{clientId}")
    public List<BookResponse> booksClientFavorites(@PathVariable Long clientId){
        return service.getBooksFavoriteClient(clientId);
    }

    @GetMapping("/operation/{clientId}")
    public List<BookResponse> getBook(@PathVariable Long clientId){
        return service.getBooksInPurchased(clientId);
    }


}
