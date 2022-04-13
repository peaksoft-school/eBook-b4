package kg.peaksoft.ebookb4.service;

import kg.peaksoft.ebookb4.dto.request.SignupRequestVendor;
import org.springframework.http.ResponseEntity;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
public interface UserService {

    ResponseEntity<?> register(SignupRequestVendor signupRequestVendor, Long number);

}
