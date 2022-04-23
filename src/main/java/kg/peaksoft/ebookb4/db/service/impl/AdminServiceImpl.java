package kg.peaksoft.ebookb4.db.service.impl;

import kg.peaksoft.ebookb4.db.models.enums.ERole;
import kg.peaksoft.ebookb4.db.repository.UserRepository;
import kg.peaksoft.ebookb4.db.service.AdminService;
import kg.peaksoft.ebookb4.dto.ResponseClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private UserRepository userRepository;

    @Override
    public List<ResponseClient> findALlClients(ERole eRole) {
        return userRepository.getAllClients(eRole);
    }

    @Override
    public void deleteById(Long id) {
         userRepository.deleteById( id);
    }


}
