package kg.peaksoft.ebookb4.service.impl;

import kg.peaksoft.ebookb4.exceptions.BadRequestException;
import kg.peaksoft.ebookb4.models.ClientRegister;
import kg.peaksoft.ebookb4.models.enums.Role;
import kg.peaksoft.ebookb4.repository.ClientRepository;
import kg.peaksoft.ebookb4.repository.UserRepository;
import kg.peaksoft.ebookb4.response.Response;
import kg.peaksoft.ebookb4.service.ClientRegisterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
@Service
@AllArgsConstructor
public class ClientRegisterServiceImpl implements ClientRegisterService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    @Override
    public Response registerUser(ClientRegister clientRegister) {
        String email = clientRegister.getEmail();


        //Is present by email
        if (clientRepository.findByEmail(email).isPresent()){
            throw new BadRequestException(
                    String.format("Client with  email = %s already exists", email)
            );
        }

        clientRegister.setPassword(encoder.encode(clientRegister.getPassword()));
        clientRegister.setRole(Role.CLIENT);
        userRepository.save(clientRegister);
        return Response.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("You have registered a user %s",
                        clientRegister.getEmail())).build();
    }
}
