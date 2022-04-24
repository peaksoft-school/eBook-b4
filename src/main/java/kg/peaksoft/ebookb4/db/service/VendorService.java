package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.dto.dto.VendorDTO;
import org.springframework.http.ResponseEntity;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
public interface VendorService {

    ResponseEntity<?> register(VendorDTO vendorDTO, Long number);
//    ResponseEntity<?> update(SignupRequestVendor signupRequestVendor, String user);

}
