package kg.peaksoft.ebookb4.api;

import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import kg.peaksoft.ebookb4.exceptions.NotFoundException;
import kg.peaksoft.ebookb4.models.ClientRegister;
import kg.peaksoft.ebookb4.response.Response;
import kg.peaksoft.ebookb4.service.ClientRegisterService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class SignUpApi {

    private final ClientRegisterService clientService;

    @PostMapping
    @PermitAll
    public Response registerUser(@Valid @RequestBody ClientRegister clientRegister){
        return clientService.registerUser(clientRegister);
    }

}
