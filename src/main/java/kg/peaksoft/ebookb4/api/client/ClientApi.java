package kg.peaksoft.ebookb4.api.client;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.service.ClientService;
import kg.peaksoft.ebookb4.db.models.entity.dto.users.ClientRegisterDTO;
import kg.peaksoft.ebookb4.db.models.entity.dto.users.ClientUpdateDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/client")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_CLIENT')")
@Tag(name = "Books",description = "crud operations")
public class ClientApi {

    private ClientService clientService;

    @Operation(summary = "Client profile", description = "Client can see all accessible data of client")
    @GetMapping("/profile")
    public ClientRegisterDTO getClientDetails(Authentication authentication){
        return clientService.getClientDetails(authentication.getName());
    }

    @Operation(summary = "Update a client", description = "Update a client profile")
    @PatchMapping("/profile/settings")
    public ResponseEntity<?> updateClient(@RequestBody ClientUpdateDTO newClientDTO, Authentication authentication){
        return clientService.update(newClientDTO, authentication.getName());


    }






}
