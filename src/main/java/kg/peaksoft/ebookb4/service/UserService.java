package kg.peaksoft.ebookb4.service;

import kg.peaksoft.ebookb4.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
public interface UserService {

    ResponseEntity<?> register(SignupRequest signupRequest, Long number);

}
