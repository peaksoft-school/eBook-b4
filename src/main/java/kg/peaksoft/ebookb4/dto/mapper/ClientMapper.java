package kg.peaksoft.ebookb4.dto.mapper;

import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.dto.ResponseClient;
import kg.peaksoft.ebookb4.dto.response.Response;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public ResponseClient create (User user){
        ResponseClient responseClient = new ResponseClient();
        responseClient.setId(user.getId());
        responseClient.setName(user.getFirstName());
        responseClient.setEmail(user.getEmail());
    return responseClient;
}}
