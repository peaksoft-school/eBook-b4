package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.dto.request.SignupRequestVendor;
import kg.peaksoft.ebookb4.dto.request.VendorRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
public interface VendorService {

    ResponseEntity<?> register(SignupRequestVendor signupRequestVendor, Long number);

    List<User> findAllVendors();

    void deleteVendor(Long id);
//    ResponseEntity<?> update(SignupRequestVendor signupRequestVendor, String user);

}
