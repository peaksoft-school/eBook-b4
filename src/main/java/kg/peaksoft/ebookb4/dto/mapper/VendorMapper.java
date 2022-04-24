package kg.peaksoft.ebookb4.dto.mapper;


import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.dto.response.VendorResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class VendorMapper {



    public VendorResponse createVendorDto(User user) {
        VendorResponse vendorResponse = new VendorResponse();
        vendorResponse.setVendorId(user.getId());
        vendorResponse.setFirstName(user.getFirstName());
        vendorResponse.setLastName(user.getLastName());
        vendorResponse.setPhoneNumber(user.getNumber());
        vendorResponse.setEmail(user.getEmail());
        vendorResponse.setAmountOfBooks(user.getVendorAddedBooks().size());
        vendorResponse.setDateOfRegistration(user.getDateOfRegistration());
        return vendorResponse;
    }

}
