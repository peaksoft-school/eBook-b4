package kg.peaksoft.ebookb4.service;


import kg.peaksoft.ebookb4.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> register(SignupRequest signupRequest, Long number);

}