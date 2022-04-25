package kg.peaksoft.ebookb4.dto.mapper;

import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.dto.dto.users.ClientRegisterDTO;
import kg.peaksoft.ebookb4.dto.dto.users.VendorRegisterDTO;
import org.springframework.stereotype.Component;

/**
 * Author: Zhanarbek Abdurasulov
 * Date: 24/4/22
 */
@Component
public class ClientRegisterMapper {

    public ClientRegisterDTO createDTO(User user){
        ClientRegisterDTO dto = new ClientRegisterDTO();
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        return dto;
    }

}
