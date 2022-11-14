package kg.peaksoft.ebookb4.db.models.mappers;

import kg.peaksoft.ebookb4.db.models.entity.User;
import kg.peaksoft.ebookb4.db.models.response.VendorResponse;
import org.springframework.stereotype.Component;

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
