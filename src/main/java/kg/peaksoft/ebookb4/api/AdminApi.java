package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.db.service.AdminService;
import kg.peaksoft.ebookb4.dto.ResponseClient;
import kg.peaksoft.ebookb4.dto.mapper.ClientMapper;
import lombok.AllArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/vendor")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Tag(name = "Admin",description = "Admin accessible apis")
public class AdminApi {

    private AdminService adminService;
    private ClientMapper clientMapper;


    @GetMapping("/getClients")
    public List<ResponseClient> getAllClient (){
    return adminService.findALlClients(ERole.ROLE_CLIENT);
    }
    @DeleteMapping("/delete")
    public  void deleteClientById(Long id){
        adminService.deleteById(id);

    }

}
