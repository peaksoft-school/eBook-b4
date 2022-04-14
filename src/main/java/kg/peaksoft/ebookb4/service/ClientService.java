package kg.peaksoft.ebookb4.service;

import kg.peaksoft.ebookb4.dto.request.SignupRequestClient;
import org.springframework.http.ResponseEntity;

public interface ClientService {

    ResponseEntity<?> register(SignupRequestClient signupRequestClient, Long number);

}
