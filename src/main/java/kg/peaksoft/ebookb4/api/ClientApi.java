package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.userClasses.Role;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.service.ClientService;
import kg.peaksoft.ebookb4.dto.dto.users.ClientRegisterDTO;
import kg.peaksoft.ebookb4.dto.dto.users.ClientUpdateDTO;
import kg.peaksoft.ebookb4.dto.dto.users.VendorRegisterDTO;
import kg.peaksoft.ebookb4.dto.dto.users.VendorUpdateDTO;
import kg.peaksoft.ebookb4.db.service.impl.ClientServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/client")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_CLIENT')")
@Tag(name = "Books",description = "crud operations")
public class ClientApi {

    private ClientService clientService;

    @Operation(summary = "Client profile", description = "All accessible data of client")
    @GetMapping("/getDetails")
    public ClientRegisterDTO getClientDetails(Authentication authentication){
        return clientService.getClientDetails(authentication.getName());
    }

    @Operation(summary = "Update", description = "Updating client")
    @PatchMapping("/update")
    public ResponseEntity<?> updateClient(@RequestBody ClientUpdateDTO newClientDTO, Authentication authentication){
        return clientService.update(newClientDTO, authentication.getName());
    }





}
