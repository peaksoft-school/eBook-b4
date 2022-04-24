package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.dto.request.SignupRequestVendor;
import kg.peaksoft.ebookb4.dto.response.VendorResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
public interface VendorService {

    ResponseEntity<?> register(SignupRequestVendor signupRequestVendor, Long number);


}
