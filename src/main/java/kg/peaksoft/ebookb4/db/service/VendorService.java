package kg.peaksoft.ebookb4.db.service;

import kg.peaksoft.ebookb4.dto.VendorRegisterDTO;
import kg.peaksoft.ebookb4.dto.VendorUpdateDTO;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;

public interface VendorService {

    ResponseEntity<?> register(VendorRegisterDTO vendorDTO, Long number);

    @Transactional
    ResponseEntity<?> update(VendorUpdateDTO newVendorDTO, String username);

    VendorRegisterDTO getVendorDetails(String username);

    ResponseEntity<?> delete(String email);

}
