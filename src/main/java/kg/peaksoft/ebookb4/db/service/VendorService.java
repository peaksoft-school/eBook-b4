package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.dto.dto.users.VendorRegisterDTO;
import kg.peaksoft.ebookb4.dto.dto.users.VendorUpdateDTO;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 12/4/22
 */
public interface VendorService {

    ResponseEntity<?> register(VendorRegisterDTO vendorDTO, Long number);

    @Transactional
    ResponseEntity<?> update(VendorUpdateDTO newVendorDTO, String username);

    VendorRegisterDTO getVendorDetails(String username);
}
