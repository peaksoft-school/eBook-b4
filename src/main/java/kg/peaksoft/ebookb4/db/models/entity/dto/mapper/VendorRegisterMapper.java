package kg.peaksoft.ebookb4.db.models.entity.dto.mapper;

import kg.peaksoft.ebookb4.db.models.entity.dto.users.VendorRegisterDTO;
import kg.peaksoft.ebookb4.db.models.entity.User;
import org.springframework.stereotype.Component;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 24/4/22
 */
@Component
public class VendorRegisterMapper {

    public VendorRegisterDTO createDTO(User user){
        VendorRegisterDTO dto = new VendorRegisterDTO();

        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setNumber(user.getNumber());
        dto.setPassword(user.getPassword());
        dto.setConfirmPassword(user.getPassword());
        return dto;
    }
}
