package kg.peaksoft.ebookb4.dto.mapper;

import kg.peaksoft.ebookb4.db.models.userClasses.User;
import kg.peaksoft.ebookb4.dto.response.ClientResponse;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientResponse createClientDto(User user){
        ClientResponse responseClient = new ClientResponse();
        responseClient.setId(user.getId());
        responseClient.setName(user.getFirstName());
        responseClient.setEmail(user.getEmail());
        responseClient.setDateOfRegistration(user.getDateOfRegistration());
    return responseClient;
}}
